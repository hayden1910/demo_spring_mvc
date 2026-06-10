--CREATE DATABASE TEST_PROBA

CREATE TABLE Invoice(
InvoiceId Int NOT NULL PRIMARY KEY IDENTITY,
CustomerId Int, 
InvoiceDate Nvarchar(120),
BillingAddress Nvarchar(120),
BillingCity Nvarchar(120),
BillingState Nvarchar(120),
BillingCountry Nvarchar(120),
BillingPostalcode Nvarchar(120),
Total NUMERIC(18, 2)
)

CREATE TABLE InvoiceLine(
InvoiceLineId Int NOT NULL PRIMARY KEY IDENTITY,
InvoiceId Int,
TrackId Int,
UnitPrice Numeric(18,2),
Quantity Int,
FOREIGN KEY (InvoiceId) REFERENCES Invoice(InvoiceId)
)

CREATE TABLE Invoice_Audit_Log (
    LogId INT IDENTITY(1,1) PRIMARY KEY,
    InvoiceId INT NOT NULL,
    Action VARCHAR(10) NOT NULL,
    OldTotal NUMERIC(10,2) NULL,
    NewTotal NUMERIC(10,2) NULL,
    ChangedBy VARCHAR(50) DEFAULT SYSTEM_USER,
    ChangedDate DATETIME DEFAULT GETDATE()
);


-- INDEX
CREATE INDEX IX_InvoiceLine_InvoiceId ON InvoiceLine(InvoiceId);


-- FUNC
CREATE FUNCTION fn_CalculateLineAmount (@Price NUMERIC(10,2), @Qty INT)
RETURNS NUMERIC(10,2)
AS
BEGIN
    RETURN ISNULL(@Price, 0) * ISNULL(@Qty, 0);
END;


-- TRG
CREATE TRIGGER trg_AfterInsertInvoiceLine
ON InvoiceLine
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @InvId INT, @Price NUMERIC(10,2), @Qty INT, @LineAmount NUMERIC(10,2);
    
    SELECT @InvId = InvoiceId, @Price = UnitPrice, @Qty = Quantity 
    FROM inserted;
    
    SET @LineAmount = dbo.fn_CalculateLineAmount(@Price, @Qty);
    
    UPDATE Invoice 
    SET Total = ISNULL(Total, 0) + @LineAmount 
    WHERE InvoiceId = @InvId;
END;

-----
CREATE TRIGGER trg_AfterInvoiceUpdate
ON Invoice
AFTER UPDATE 
AS
BEGIN
    IF UPDATE(Total)
    BEGIN 
        INSERT INTO Invoice_Audit_Log (
            InvoiceId,
            [Action],
            OldTotal,
            NewTotal
        )
        SELECT 
            d.InvoiceId,
            'UPDATE' AS [Action],
            d.Total AS OldTotal,
            i.Total AS NewTotal
        FROM deleted d
        JOIN inserted i ON d.InvoiceId = i.InvoiceId;
    END
END


-- SP
CREATE PROCEDURE sp_InsertInvoiceLine
    @InvoiceId INT,
    @TrackId INT,
    @UnitPrice NUMERIC(10,2),
    @Quantity INT
AS
BEGIN
    SET NOCOUNT ON;
    
    INSERT INTO InvoiceLine (InvoiceId, TrackId, UnitPrice, Quantity)
    VALUES (@InvoiceId, @TrackId, @UnitPrice, @Quantity);
END;

EXEC sp_InsertInvoiceLine 412, 2, 1.2, 2

SELECT * FROM Invoice i LEFT JOIN InvoiceLine il ON i.InvoiceId = il.InvoiceId;


