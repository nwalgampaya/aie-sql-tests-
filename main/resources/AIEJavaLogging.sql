USE [master]
GO
/****** Object:  Database [AIEJavaLogging]    Script Date: 9/03/2018 2:21:49 PM ******/
CREATE DATABASE [AIEJavaLogging]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'AIEJavaLogging', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.ASCOM\MSSQL\DATA\AIEJavaLogging.mdf' , SIZE = 3136KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'AIEJavaLogging_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.ASCOM\MSSQL\DATA\AIEJavaLogging_log.ldf' , SIZE = 832KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [AIEJavaLogging] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [AIEJavaLogging].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [AIEJavaLogging] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET ARITHABORT OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [AIEJavaLogging] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [AIEJavaLogging] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [AIEJavaLogging] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET  DISABLE_BROKER 
GO
ALTER DATABASE [AIEJavaLogging] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [AIEJavaLogging] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [AIEJavaLogging] SET  MULTI_USER 
GO
ALTER DATABASE [AIEJavaLogging] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [AIEJavaLogging] SET DB_CHAINING OFF 
GO
ALTER DATABASE [AIEJavaLogging] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [AIEJavaLogging] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [AIEJavaLogging]
GO
/****** Object:  Schema [AIE]    Script Date: 9/03/2018 2:21:49 PM ******/
CREATE SCHEMA [AIE]
GO
/****** Object:  Table [AIE].[ErrorDetails]    Script Date: 9/03/2018 2:21:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AIE].[ErrorDetails](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[AIEId] [nvarchar](40) NULL,
	[AscomMsgId] [nvarchar](20) NULL,
	[ErrorNumber] [int] NULL,
	[ErrorSeverity] [int] NULL,
	[ErrorState] [int] NULL,
	[ErrorProcedure] [nvarchar](128) NULL,
	[ErrorLine] [int] NULL,
	[ErrorMessage] [nvarchar](MAX) NULL,
 CONSTRAINT [PK_Id_ErrorDetails] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [AIE].[ProcessedRequest]    Script Date: 9/03/2018 2:21:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AIE].[ProcessedRequest](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[AIEId] [nvarchar](40) NULL,
	[AscomMsgId] [nvarchar](20) NULL,
	[UniteLogId] [nvarchar](20) NULL,
	[Timestamp] [datetime2](7) NOT NULL,
	[Port] [int] NULL,
	[Version] [nvarchar](10) NULL,
	[CallId] [nvarchar](20) NULL,
	[OAPRequest] [text] NULL,
	[Subject] [text] NULL,
	[Beepcode] [tinyint] NULL,
	[Priority] [tinyint] NULL,
	[Tag] [text] NULL,
	[OAPType] [nvarchar](40) NULL,
	[FaultStatus] [int] NULL,
	[FaultDetails] [text] NULL,
 CONSTRAINT [PK_ProcessedId_ProcessedRequest] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [AIE].[ReponseReceived]    Script Date: 9/03/2018 2:21:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AIE].[ReponseReceived](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[AscomMsgId] [nvarchar](20) NULL,
	[Timestamp] [datetime2](7) NOT NULL,
	[Status] [int] NULL,
	[Provider] [nvarchar](50) NULL,
	[Type] [nvarchar](50) NULL,
	[OAPVersion] [nvarchar](10) NULL,
	[FaultStatus] [int] NULL,
	[FaultDetails] [text] NULL,
	[OAPResponse] [text] NULL,
 CONSTRAINT [PK_ResponseId_ReponseReceived] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [AIE].[RequestReceived]    Script Date: 9/03/2018 2:21:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [AIE].[RequestReceived](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[AIEId] [nvarchar](40) NULL,
	[Timestamp] [datetime2](7) NOT NULL,
	[Body] [text] NULL,
	[EventType] [nvarchar](50) NULL,
	[AlertType] [nvarchar](50) NULL,
	[FaultStatus] [int] NULL,
	[FaultDetails] [text] NULL,
 CONSTRAINT [PK_RequestId_RequestReceived] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

/****** Object:  StoredProcedure [AIE].[AddErrorDetails] ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [AIE].[AddErrorDetails]
	  @AIEId nvarchar(40),
	  @AscomMsgId nvarchar(40),
	  @ErrorNumber int,
	  @ErrorSeverity int,
	  @ErrorState int,
	  @ErrorProcedure nvarchar(128),
	  @ErrorLine int,
	  @ErrorMessage text
AS
BEGIN  
  
		INSERT INTO AIE.ErrorDetails (AIEId, AscomMsgId, ErrorNumber, ErrorSeverity, ErrorState, ErrorProcedure, ErrorLine, ErrorMessage)
		VALUES (@AIEId, @AscomMsgId, @ErrorNumber, @ErrorSeverity, @ErrorState, @ErrorProcedure, @ErrorLine, @ErrorMessage)
		
END
GO
/****** Object:  StoredProcedure [AIE].[AddProcessedRequest]    Script Date: 9/03/2018 2:21:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [AIE].[AddProcessedRequest]
      @FaultStatus int,
      @FaultDetails text,
	  @AIEId nvarchar(40),
	  @AscomMsgId nvarchar(20),
	  @UniteLogId nvarchar(20),
	  @Timestamp datetime2,
	  @Port int,
	  @Version nvarchar(10),
	  @callId nvarchar(20),
	  @OAPRequest text,
	  @Subject text,
	  @BeepCode tinyint,
	  @Priority tinyint,
	  @Tag text,
	  @OAPType nvarchar(40),
	  @rowid int OUTPUT
AS
BEGIN TRY  
	DECLARE
      @ErrNo int,
	  @ErrorMsg nvarchar(4000),
	  @ErrorLine int,
	  @ErrorState int,
	  @ErrorSeverity int,
	  @ErrorProc nvarchar(128)
      INSERT INTO AIE.ProcessedRequest (AIEId, AscomMsgId, UniteLogId, Timestamp, Port, Version, callId, OAPRequest, Subject, BeepCode, Priority, Tag, OAPType, FaultStatus, FaultDetails)
      VALUES (@AIEId,  @AscomMsgId,  @UniteLogId, @Timestamp, @Port,  @Version, @callId, @OAPRequest, @Subject, @BeepCode, @Priority, @Tag, @OAPType, @FaultStatus, @FaultDetails)  
	   SET @rowid = SCOPE_IDENTITY();
END TRY
BEGIN CATCH
   SELECT   @ErrNo = ERROR_NUMBER(), @ErrorMsg = ERROR_MESSAGE(), @ErrorLine = ERROR_LINE(), @ErrorState = ERROR_STATE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorProc= ERROR_PROCEDURE()
	EXEC  [AIE].AddErrorDetails @AIEId,NULL ,@ErrNo, @ErrorSeverity, @ErrorState, @ErrorProc, @ErrorLine, @ErrorMsg;
	SET @rowid = -1;
END CATCH
GO
/****** Object:  StoredProcedure [AIE].[AddRequestReceived]    Script Date: 9/03/2018 2:21:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [AIE].[AddRequestReceived]
      @FaultStatus int,
      @FaultDetails text,
	  @AIEId nvarchar(40),
	  @Timestamp datetime2,
	  @Body text,
	  @Eventtype nvarchar(50),
	  @AlertType nvarchar(50),
	  @rowid int OUTPUT

AS
BEGIN TRY
	DECLARE
      @ErrNo int,
	  @ErrorMsg nvarchar(4000),
	  @ErrorLine int,
	  @ErrorState int,
	  @ErrorSeverity int,
	  @ErrorProc nvarchar(128)
	  
       INSERT INTO AIE.RequestReceived (AIEId, Timestamp, Body, EventType, AlertType, FaultStatus, FaultDetails)
	  VALUES (@AIEId, @Timestamp, @Body, @EventType, @AlertType, @FaultStatus, @FaultDetails) 
	 SET @rowid = SCOPE_IDENTITY();
END TRY
BEGIN CATCH
	SELECT   @ErrNo = ERROR_NUMBER(), @ErrorMsg = ERROR_MESSAGE(), @ErrorLine = ERROR_LINE(), @ErrorState = ERROR_STATE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorProc= ERROR_PROCEDURE()
	EXEC  [AIE].AddErrorDetails @AIEId, NULL, @ErrNo, @ErrorSeverity, @ErrorState, @ErrorProc, @ErrorLine, @ErrorMsg;
	SET @rowid = -1;
END CATCH

GO
/****** Object:  StoredProcedure [AIE].[AddResponseReceived]    Script Date: 9/03/2018 2:21:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [AIE].[AddResponseReceived]	
      @FaultStatus int,
      @FaultDetails text,
	  @AscomMsgId nvarchar(20),
	  @Timestamp datetime2,
	  @ResponseStatus int,
	  @Provider nvarchar(50),
	  @Type nvarchar(50),
	  @OAPVersion nvarchar(10),
	  @OAPResponse text,
	  @rowid int OUTPUT
AS
BEGIN TRY
	DECLARE
      @ErrNo int,
	  @ErrorMsg nvarchar(4000),
	  @ErrorLine int,
	  @ErrorState int,
	  @ErrorSeverity int,
	  @ErrorProc nvarchar(128)

	  INSERT INTO  AIE.ReponseReceived(AscomMsgId, Timestamp, Status, Provider, Type, OAPVersion, FaultStatus, FaultDetails, OAPResponse)
	  VALUES (@AscomMsgId, @Timestamp, @ResponseStatus, @Provider, @Type, @OAPVersion, @FaultStatus, @FaultDetails, @OAPResponse)
	  SET @rowid = SCOPE_IDENTITY();
END TRY
BEGIN CATCH
    SELECT   @ErrNo = ERROR_NUMBER(), @ErrorMsg = ERROR_MESSAGE(), @ErrorLine = ERROR_LINE(), @ErrorState = ERROR_STATE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorProc= ERROR_PROCEDURE()
	EXEC  [AIE].AddErrorDetails NULL, @AscomMsgId,@ErrNo, @ErrorSeverity, @ErrorState, @ErrorProc, @ErrorLine, @ErrorMsg;
	SET @rowid = -1;
END CATCH
GO
	
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [AIE].[getLogDetails]
   @startDate datetime = NULL, 
   @endDate datetime = NULL,
   @eventType nvarchar(50) = NULL,
   @alertType nvarchar(50) = NULL,
   @success bit = false,
   @failure bit = false,
   @incomingFailure bit = false,
   @rowCount int OUTPUT
AS 
BEGIN TRY
   DECLARE 
    @sql nvarchar(MAX),
	@paramList nvarchar(1000),
    @ErrNo int,
	@ErrorMsg nvarchar(MAX),
	@ErrorLine int,
	@ErrorState int,
	@ErrorSeverity int,
	@ErrorProc nvarchar(128)

   SELECT @paramList = ' @startDate datetime, @endDate datetime,  @eventType nvarchar(50), @alerttype nvarchar(50), @success bit, @failure bit, @incomingFailure bit'
   SELECT @sql = 'SELECT RR.Timestamp as ReceivedRequestTime, 
						 RR.Body as ReceivedRequestBody, 
						 RR.EventType as EventType, 
						 RR.AlertType as AlertType, 
						 RR.FaultStatus as ReceivedRequestFaultStatus,
						 RR.FaultDetails as ReceivedRequestFaultDetails,
						 PR.UniteLogId as UniteLogId,
						 PR.Timestamp as ProcessedRequestTime,
						 PR.Port as Port, 
						 PR.Version as OAPVersion,
						 PR.CallId as CallId,
						 PR.OAPRequest as ProcessedRequestBody,
						 PR.Subject as Subject,
						 PR.Beepcode as BeepCode,
						 PR.Priority as Priority,
						 PR.Tag as Tag,
						 PR.OAPType as OAPType,
						 PR.FaultStatus as ProcessedRequestFaultStatus,
						 PR.FaultDetails as ProcessedRequestFaultDetails,
						 RESP.Status as ReceivedResponseStatus,
						 RESP.Timestamp as ResponsereceivedTime,
						 RESP.FaultStatus as ReceivedResponseFaultStatus,
						 RESP.FaultDetails as ReceivedResponseFaultDetails
						 FROM AIE.RequestReceived RR LEFT JOIN AIE.ProcessedRequest PR ON RR.AIEId = PR.AIEId 
						 LEFT JOIN AIE.ReponseReceived RESP ON RESP.AscomMsgId = PR.AscomMsgId  WHERE (1=1)'

   if @success = 1 AND @failure = 1
	 SET @failure = 0
   
   if @startDate IS NULL AND @endDate IS NULL BEGIN
     SET @startDate = convert(datetime, FORMAT(GetDate() -1, 'yyyy-MM-dd hh:mm'))
	 SET @endDate = convert(datetime, FORMAT(GetDate(), 'yyyy-MM-dd hh:mm'))
   END
   print @startDate
   print @endDate
  
   if @startDate IS NOT NULL AND @endDate IS NOT NULL  
     SELECT @sql = @sql + ' AND (RR.Timestamp BETWEEN @startDate AND @endDate) '
   
   if @eventType IS NOT NULL AND @eventType <> '' 
     SELECT @sql = @sql + ' AND RR.EventType =  @eventType '

   if @alertType IS NOT NULL AND @alertType <> '' 
     SELECT @sql = @sql + ' AND RR.AlertType = @alertType '
  
   if @success = 1
     SELECT @sql = @sql + ' AND RR.FaultStatus IS NULL AND PR.FaultStatus IS NULL AND RESP.FaultStatus IS NULL '
   if @failure = 1
     SELECT @sql = @sql + ' AND (RR.FaultStatus IS NOT NULL OR PR.FaultStatus IS NOT NULL OR RESP.FaultStatus IS NOT NULL)'
   if @incomingFailure = 1
     SELECT @sql = @sql + ' AND RR.FaultStatus IS NOT NULL '
   
   PRINT @sql
   
   EXEC sp_executesql @sql, @paramList, @startDate, @endDate, @eventType, @alertType, @success ,@failure, @incomingFailure
   SET @rowCount = @@ROWCOUNT;
   RETURN
END TRY

BEGIN CATCH

	SELECT   @ErrNo = ERROR_NUMBER(), @ErrorMsg = ERROR_MESSAGE(), @ErrorLine = ERROR_LINE(), @ErrorState = ERROR_STATE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorProc= ERROR_PROCEDURE()
    EXEC  [AIE].[AddErrorDetails] NULL, NULL, @ErrNo, @ErrorSeverity, @ErrorState, @ErrorProc, @ErrorLine, @ErrorMsg;
	SET @rowCount = -1;

END CATCH
GO
USE [master]
GO
ALTER DATABASE [AIEJavaLogging] SET  READ_WRITE 
GO
