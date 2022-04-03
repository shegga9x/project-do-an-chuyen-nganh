CREATE DATABASE Course_Registration
GO
USE Course_Registration
SET DATEFORMAT DMY
GO

--dữ liệu bảng Privilege
CREATE SEQUENCE [dbo].[hibernate_sequence] 
 AS [bigint]
 START WITH 10000000
 INCREMENT BY 1
 MINVALUE -9223372036854775808
 MAXVALUE 9223372036854775807
 CACHE 
 go
create table Role
(
  [id] [int] IDENTITY(1,1) NOT NULL,
  [role_name] [int] NOT NULL,
  PRIMARY KEY ([id])
)
--dữ liệu bảng Privilege
create table Privilege
(
  id int IDENTITY(1,1) PRIMARY KEY,
  ID_Role int NOT NULL FOREIGN KEY REFERENCES Role ([id]),
  Name_Privilege nvarchar(50) not null,
)
CREATE TABLE ACCOUNT
(
  ID_ACCOUNT nvarchar(50) NOT NULL,
  --Admin là ad ,student là st,professor là pr
  email nvarchar(50) NOT NULL,
  password_hash nvarchar(255) NOT NULL,
  [created] [datetime2](7) ,
  [updated] [datetime2](7) ,
  [last_expires] [datetime2](7) ,
  [accept_terms] [bit] ,
  PRIMARY KEY (ID_ACCOUNT)
)
CREATE TABLE ACCOUNT_Has_Role
(
  ID_ACCOUNT nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT),
  [id] int NOT NULL FOREIGN KEY REFERENCES Role ([id]),
  PRIMARY KEY (ID_ACCOUNT,[id])
)
CREATE TABLE ACCOUNT_Detail
(
  ID_ACCOUNT nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT),
  first_name nvarchar(max) ,
  last_name nvarchar(max) ,
  address nvarchar(50)  ,
  phone_number varchar(10) ,
  birthday smalldatetime ,
  PRIMARY KEY (ID_ACCOUNT)
)
CREATE TABLE Refresh_Token
(
  [id] [int] IDENTITY(1,1) NOT NULL,
  [token] [nvarchar](max) NULL,
  [expires] [datetime2](7) NOT NULL,
  [created] [datetime2](7) NOT NULL,
  [created_by_ip] [nvarchar](max) NULL,
  [revoked] [datetime2](7) NULL,
  [revoked_by_ip] [nvarchar](max) NULL,
  [replaced_by_token] [nvarchar](max) NULL,
  [reason_revoked] [nvarchar](max) NULL,
  [account_id] nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT),
  PRIMARY KEY ([id])
)
CREATE TABLE Verification_Token
(
  ID_ACCOUNT nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT),
  [verification_token_content] [nvarchar](max) NULL,
  [verified] [datetime2](7) NULL,
  PRIMARY KEY (ID_ACCOUNT)
)
CREATE TABLE Reset_Token
(
  ID_ACCOUNT nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT),
  [reset_token_content] [nvarchar](max) NULL,
  [reset_token_expires] [datetime2](7) NULL,
  [password_reset] [datetime2](7) NULL,
  PRIMARY KEY (ID_ACCOUNT)
)
CREATE TABLE Faculty
(
  -- mã khoa (VD DH18DTA thì mã khoa là DT)
  ID_Faculty nvarchar(50) NOT NULL,
  ID_Faculty_N int NOT NULL,
  Name_Faculty nvarchar(50) NOT NULL,

  PRIMARY KEY (ID_Faculty)
)
CREATE TABLE Clazz
(
  -- mã khoa (VD DH18DTA thì mã khoa là DT)
  Clazz_code nvarchar(50) NOT NULL,
  ID_Faculty nvarchar(50) NOT NULL,
  Max_Size tinyint NOT NULL,
  Current_Size tinyint NOT NULL,
  PRIMARY KEY (Clazz_code)
)
CREATE TABLE Student
(
  ID_Student nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT) ON DELETE CASCADE,
  Student_Name nvarchar(50) NOT NULL,
  -- mã khoa
  ID_Faculty nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Faculty (ID_Faculty),
  -- Khóa học của trường (VD DH18DTA thì khóa của trường là 18 vì mình nộp hồ sơ năm 2018)
  -- Cách tính khóa học lấy năm nộp hồ sơ (trong đây là năm tạo tài khoản) trừ cho năm mốc (năm 2000)
  Create_date smalldatetime NOT NULL,
  -- Clazz code là mã lớp (VD DH18DTA)
  Clazz_code nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Clazz (Clazz_code),
  -- Thêm vào số chứng chỉ bắt buộc ra trường
  -- Ngành CNTT thì tao nhớ ko nhầm tích lũy 145 chỉ là dc ra trường
  Cert_number_required smallint NOT NULL,
  -- Tính năng mới
  -- Số chứng chỉ đã tích lũy được
  Cert_number_accumulated smallint NOT NULL,
  -- Tính năng mới
  PRIMARY KEY (ID_Student)
)
CREATE TABLE Professor
(
  ID_Professor nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT) ON DELETE CASCADE,
  Professor_Name nvarchar(50) NOT NULL,
  -- mã khoa
  ID_Faculty nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Faculty (ID_Faculty),
  -- Khóa học của trường (VD DH18DTA thì khóa của trường là 18 vì mình nộp hồ sơ năm 2018)
  -- Cách tính khóa học lấy năm nộp hồ sơ (trong đây là năm tạo tài khoản) trừ cho năm mốc (năm 2000)
  Create_date date NOT NULL,
  -- Degree là cái vẹo gì
  Degree nvarchar(50),
  PRIMARY KEY (ID_Professor)
)
CREATE TABLE Semester
(
  ID_Semester nvarchar(50) NOT NULL,
  start_Date date NOT NULL,
  end_Date date NOT NULL,
  years smallint,
  number_S smallint NOT NULL
    PRIMARY KEY (ID_Semester)
)
CREATE TABLE Time_For_Course_Register
(
  ID_Semester nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Semester (ID_Semester),
  start_Date date NOT NULL,
  end_Date date NOT NULL,
  PRIMARY KEY (ID_Semester)
)
CREATE TABLE Course
(
  ID_Course nvarchar(50) NOT NULL,
  ID_Faculty nvarchar(50) FOREIGN KEY REFERENCES Faculty (ID_Faculty),
  Name_Course nvarchar(50) NOT NULL,
  -- số chứng chỉ học phần (cao nhất là 4 nên để tinyint)	
  Course_certificate tinyint NOT NULL,
  -- học viên năm bao nhiêu có thể học
  years int ,
  -- học kì cố định có môn này sẽ mở nếu/ hk sẽ là 1, 2  / nếu ko thì sẽ là null
  number_S smallint
    PRIMARY KEY (ID_Course)
)
CREATE TABLE Course_Offering
(
  ID_Course_Offering nvarchar(50) NOT NULL,
  ID_Course nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Course (ID_Course) ON DELETE CASCADE,
  -- mã lớp (VD DH18DTA)
  Clazz_code nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Clazz (Clazz_code),
  -- tinyint max value là 255 (từ 0 đến 255)
  -- max size là số sinh viên tối đa của lớp
  Max_Size tinyint NOT NULL,
  -- current size là số sinh viên đang hiện có của lớp
  Current_Size tinyint NOT NULL,
  -- lịch học thì sẽ có ngày bắt đầu và ngày kết thúc 
  -- ngày bắt đầu
  PRIMARY KEY (ID_Course_Offering)
)
CREATE TABLE Schedule
(
  ID_Schedule nvarchar(50) NOT NULL,
  ID_Course_Offering nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Course_Offering (ID_Course_Offering) ON DELETE CASCADE,
  -- Mã giáo viên
  -- Sửa lại ID_Professor có thể là null
  Id_Profeesor nvarchar(50) FOREIGN KEY REFERENCES Professor (ID_Professor),
  -- TH là thực hành
  -- LT là lý thuyết
  Theoretical varchar(2) NOT NULL,
  -- thứ dạy học
  Teaching_Day smallint NOT NULL,
  -- ngày bắt đầu
  Start_Day date NOT NULL,
  -- ngày kết thúc
  End_Day date NOT NULL,
  -- Địa điểm học
  Study_place nvarchar(50) NOT NULL,
  -- tiết bắt đầu
  Start_Slot tinyint NOT NULL,
  -- tiết kết thúc
  End_Slot tinyint NOT NULL,
  PRIMARY KEY (ID_Schedule)
)
CREATE TABLE Student_Schedule
(
  ID_Semester nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Semester (ID_Semester),
  ID_Schedule nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Schedule (ID_Schedule) ON DELETE CASCADE,
  ID_Student nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Student (ID_Student) ON DELETE CASCADE,
  PRIMARY KEY (ID_Student, ID_Semester, ID_Schedule)
)
CREATE TABLE Student_Schedule_R
(
  ID_Semester nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Semester (ID_Semester),
  ID_Schedule nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Schedule (ID_Schedule) ON DELETE CASCADE,
  ID_Student nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Student (ID_Student) ON DELETE CASCADE,
  PRIMARY KEY (ID_Student, ID_Semester, ID_Schedule)
)
CREATE TABLE Professor_Schedule
(
  ID_Semester nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Semester (ID_Semester),
  ID_Schedule nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Schedule (ID_Schedule) ON DELETE CASCADE,
  ID_Professor nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Professor (ID_Professor) ON DELETE CASCADE,
  PRIMARY KEY (ID_Professor, ID_Semester, ID_Schedule)
)
CREATE TABLE Front_Sub
(
  ID_Course_B nvarchar(50),
  ID_Course nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Course (ID_Course),
  PRIMARY KEY (ID_Course)
)
CREATE TABLE Sub_Pass -- sub pass ko co id mon hoc la vi id mon hoc trong schedule /thay hoi sai =))
(
  -- Học kỳ
  ID_Semester nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Semester (ID_Semester),
  ID_Course nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Course (ID_Course) ON DELETE CASCADE,
  ID_Student nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Student (ID_Student) ON DELETE CASCADE,
  -- Điểm	
  Score float NOT NULL CHECK (Score <= 10 AND Score >= 0),
  -- Điểm hệ 4
  Score_System_4 float NOT NULL CHECK (Score_System_4 <= 4 AND Score_System_4 >= 0),
  -- Đánh giá học lực
  Rated nvarchar(10) NOT NULL,
  PRIMARY KEY (ID_Student, ID_Course, ID_Semester)
)
CREATE TABLE Semester_Result
(
  ID_Semester nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Semester (ID_Semester),
  ID_Student nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Student (ID_Student) ON DELETE CASCADE,
  --diem trung binh trong ki nay 
  grade_Av float,
  --diem trung binh he 4 trong ki nay
  grade_Av_4 float,
  credit_Get smallint,
  PRIMARY KEY (ID_Semester, ID_Student)
)
CREATE TABLE Final_Result
(
  ID_Student nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Student (ID_Student),
  grade_Av float,
  grade_Av_4 float,
  PRIMARY KEY (ID_Student)
)
CREATE TABLE Billing_System
(
  ID_Semester nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Semester (ID_Semester),
  ID_Student nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Student (ID_Student),
  Paymoney float,
  creadit smallint,
  PRIMARY KEY (ID_Semester, ID_Student)
)

-- function

GO
-- thời khóa biểu có học sinh , thời khóa biểu của giáo viên chỉ cần gọi schdule
CREATE FUNCTION Time_Table_St (@ID_ACCOUNT varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT sd.ID_Schedule,
  sd.ID_Course_Offering
FROM Schedule sd JOIN
  Course_Offering co ON sd.ID_Course_Offering = co.ID_Course_Offering JOIN
  Student_Schedule stc ON stc.ID_Schedule = sd.ID_Schedule
WHERE stc.ID_Student = @ID_ACCOUNT
  AND stc.ID_Semester IN (SELECT
    ID_Semester
  FROM Semester
  WHERE GETDATE() BETWEEN start_Date AND end_Date)

GO
CREATE FUNCTION sub_Passed (@ID_Course_B nvarchar(50), @ID_ACCOUNT nvarchar(50))
RETURNS nvarchar(50)
AS
BEGIN
  DECLARE @ID_Course_B1 nvarchar(50)
  (SELECT
    @ID_Course_B1 = fs.ID_Course_B
  FROM front_Sub fs
  WHERE fs.ID_Course_B = @ID_Course_B
    AND fs.ID_Course =
                    CASE
                      WHEN (SELECT
      ID_Course
    FROM Sub_Pass
    WHERE ID_Course = fs.ID_Course
      AND ID_Student = @ID_ACCOUNT
      AND Score >= 4)
                        IS NOT NULL THEN fs.ID_Course
                      ELSE NULL
                    END)
  RETURN @ID_Course_B1;
END
GO

-- những môn sẽ hiển thị khi nhấn đăng k
-- những môn có thể đăng ký của giáo viên thì chọn những môn nào trong bảng schedule có chỗ id pr là nullý môn học
CREATE FUNCTION Sub_Available_ST (@ID_ACCOUNT varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT sc.ID_Schedule
FROM Course_Offering co JOIN
  Course c ON c.ID_Course = co.ID_Course JOIN
  Schedule sc ON sc.ID_Course_Offering = co.ID_Course_Offering

WHERE c.ID_Faculty =
                      CASE
                        WHEN c.ID_Faculty IS NULL THEN c.ID_Faculty
                        ELSE (SELECT
    ID_Faculty
  FROM Student
  WHERE ID_Student = @ID_ACCOUNT)
                      END
  AND c.years =
               CASE
                 WHEN c.years IS NULL THEN c.years
                 ELSE (SELECT
    (YEAR(GETDATE()) - YEAR(Create_date))
  FROM Student
  WHERE ID_Student = @ID_ACCOUNT)
               END
  AND c.number_S =
                  CASE
                    WHEN c.number_S = 0 THEN c.number_S
                    ELSE (SELECT
    number_S
  FROM Semester
  WHERE ID_Semester IN (SELECT
    ID_Semester
  FROM Semester
  WHERE GETDATE() BETWEEN start_Date AND end_Date))
                  END
  AND c.ID_Course =
                   CASE
                     WHEN (SELECT
    ID_Course_B
  FROM front_Sub
  WHERE ID_Course_B = c.ID_Course)
                       IS NULL THEN c.ID_Course
                     WHEN [dbo].sub_Passed(c.ID_Course, @ID_ACCOUNT) IS NULL THEN NULL
                     ELSE [dbo].sub_Passed(c.ID_Course, @ID_ACCOUNT)
                   END
  AND CAST(SUBSTRING(co.Clazz_code,3,2) AS int) = (( YEAR( GETDATE() ) % 100 )) + 1 - (c.number_S - 1) - c.years


  
GO

select * from ACCOUNT
select * from Sub_Available_ST('18130077');

-- bảng này là bảng check khi nhấn vào ô chọn môn học nếu trùng giờ trùng ngày , trùng môn nếu rỗng thì ko đk được
GO

CREATE FUNCTION check_Start_Slot_TeachDay (@ID_ACCOUNT varchar(50), @Start_Slot varchar(50), @Teaching_Day varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT sc.Start_Slot
FROM Schedule sc JOIN
  Student_Schedule stc ON sc.ID_Schedule = stc.ID_Schedule
WHERE stc.ID_Student = @ID_ACCOUNT
  AND stc.ID_Semester IN (SELECT
    ID_Semester
  FROM Semester
  WHERE GETDATE() BETWEEN start_Date AND end_Date)
  AND sc.Start_Slot = @Start_Slot
  AND sc.Teaching_Day = @Teaching_Day
GO
CREATE FUNCTION check_Sub_Exist (@ID_ACCOUNT varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT c.ID_Course
FROM Schedule sc JOIN
  Student_Schedule stc ON sc.ID_Schedule = stc.ID_Schedule JOIN
  Course_Offering co ON co.ID_Course_Offering = sc.ID_Course_Offering JOIN
  Course c ON c.ID_Course = co.ID_Course
WHERE stc.ID_Student = @ID_ACCOUNT
  AND stc.ID_Semester IN (SELECT
    ID_Semester
  FROM Semester
  WHERE GETDATE() BETWEEN start_Date AND end_Date)
GO
CREATE FUNCTION check_DayST (@ID_Schedule nvarchar(50), @ID_ACCOUNT varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT sc.ID_Schedule
FROM Schedule sc JOIN
  Course_Offering co ON co.ID_Course_Offering = sc.ID_Course_Offering
WHERE (sc.Start_Slot IN (SELECT
    Start_Slot
  FROM check_Start_Slot_TeachDay(@ID_ACCOUNT, sc.Start_Slot, sc.Teaching_Day))
  OR co.ID_Course IN (SELECT
    ID_Course
  FROM check_Sub_Exist(@ID_ACCOUNT))
  )
  AND (co.Current_Size < co.Max_Size)
  AND sc.ID_Schedule = @ID_Schedule
GO
CREATE FUNCTION check_Sub_Exist_For_Professor (@ID_ACCOUNT varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT c.ID_Course
FROM Schedule sc JOIN
  Professor_Schedule prc ON sc.ID_Schedule = prc.ID_Schedule JOIN
  Course_Offering co ON co.ID_Course_Offering = sc.ID_Course_Offering JOIN
  Course c ON c.ID_Course = co.ID_Course
WHERE prc.ID_Professor = @ID_ACCOUNT
  AND prc.ID_Semester IN (SELECT
    ID_Semester
  FROM Semester
  WHERE GETDATE() BETWEEN start_Date AND end_Date)
GO

-- Thời khóa biểu cho giáo viên
CREATE FUNCTION Time_Table_Pr (@ID_Professor varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT sd.*
FROM Schedule sd JOIN
  Course_Offering co ON sd.ID_Course_Offering = co.ID_Course_Offering JOIN
  Professor_Schedule prc ON prc.ID_Schedule = sd.ID_Schedule
WHERE prc.ID_Professor = @ID_Professor
  AND prc.ID_Semester IN (SELECT
    ID_Semester
  FROM Semester
  WHERE GETDATE() BETWEEN start_Date AND end_Date)
GO
--Phương thức check những môn mà giáo viên có thể đk ký
CREATE FUNCTION check_Subject_For_Professor (@ID_Professor nvarchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT sc.ID_Schedule
FROM Schedule sc JOIN
  Course_Offering co ON sc.ID_Course_Offering = co.ID_Course_Offering JOIN
  Course c ON co.ID_Course = c.ID_Course
WHERE sc.Id_Profeesor IS NULL
  AND (c.ID_Faculty = (SELECT
    pf.ID_Faculty
  FROM Professor pf
  WHERE pf.ID_Professor = @ID_Professor)
  OR c.ID_Faculty IS NULL);
-- Phương thức check có trùng ngày và giờ ko
go
CREATE FUNCTION check_Start_Slot_Teach_Day_PR (@ID_Professor varchar(50), @Start_Slot varchar(50), @Teaching_Day varchar(50))
RETURNS TABLE
AS
  RETURN	
  SELECT sc.Start_Slot
FROM Schedule sc JOIN
  Professor_Schedule prc ON sc.ID_Schedule = prc.ID_Schedule
WHERE prc.ID_Professor = @ID_Professor
  AND prc.ID_Semester IN (SELECT
    ID_Semester
  FROM Semester
  WHERE GETDATE() BETWEEN start_Date AND end_Date)
  AND prc.ID_Semester IN (SELECT
    ID_Semester
  FROM Semester
  WHERE GETDATE() BETWEEN start_Date AND end_Date)
  AND sc.Start_Slot = @Start_Slot
  AND sc.Teaching_Day = @Teaching_Day
GO

--Phương thức đk cho giáo viên
CREATE FUNCTION check_Day_Pr (@ID_Schedule nvarchar(50), @ID_Professor varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT sc.ID_Schedule
FROM Schedule sc JOIN
  Course_Offering co ON co.ID_Course_Offering = sc.ID_Course_Offering
WHERE (sc.Start_Slot IN (SELECT
    Start_Slot
  FROM check_Start_Slot_Teach_Day_PR(@ID_Professor, sc.Start_Slot, sc.Teaching_Day))
  OR co.ID_Course IN (SELECT
    ID_Course
  FROM check_Sub_Exist_For_Professor(@ID_Professor))
  )
  AND sc.ID_Schedule = @ID_Schedule
--and (select count(ID_Schedule) from Professor_Schedule where ID_Professor = @ID_Professor) <= 3
GO
-- tạo function semester_Result	

CREATE FUNCTION get_Semester_Reuslt (@ID_Student nvarchar(50), @ID_Semester nvarchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT c.ID_Course,
  c.Name_Course,
  c.Course_certificate,
  sp.Score,
  sp.Score_System_4
FROM Sub_Pass sp JOIN
  Course c ON sp.ID_Course = c.ID_Course

WHERE sp.ID_Student = @ID_Student
  AND sp.ID_Semester = @ID_Semester
GO

GO
CREATE TRIGGER check_Course_Offering
ON Course_Offering
FOR INSERT, UPDATE
AS
BEGIN
  DECLARE @CurrentSizeI tinyint = (SELECT
    I.Current_Size
  FROM inserted I);
  DECLARE @MaxSizeI tinyint = (SELECT
    I.Max_Size
  FROM inserted I);
  IF @CurrentSizeI > @MaxSizeI
  BEGIN
    RAISERROR (N'lớp đã đầy', 11, 1)
    ROLLBACK TRANSACTION
  END
END

GO


go

-- insert Record

-- dữ liệu bảng Role
INSERT INTO Role VALUES (0)
INSERT INTO Role VALUES (1)
INSERT INTO Role VALUES (2)

-- insert into ACCOUNT
insert into ACCOUNT Values(N'18130005',N'18130005@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'18130077',N'18130077@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'18130001',N'18130001@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'18130002',N'18130002@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)	
insert into ACCOUNT Values(N'18130003',N'18130003@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'18130004',N'18130004@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'18130006',N'18130006@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'19130006',N'19130006@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'19130007',N'19130007@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'20130006',N'20130006@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'20130007',N'20130007@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)


insert into ACCOUNT Values('pdt','pdt@st.hcmuaf.edu.vn','$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)


insert into ACCOUNT Values(N'224',N'224@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'225',N'225@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'226',N'226@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'227',N'227@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'228',N'228@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'229',N'229@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'220',N'220@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)
insert into ACCOUNT Values(N'300',N'300@st.hcmuaf.edu.vn',N'$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS',getdate(),null,null,1)


--dữ liệu bảng Faculty
INSERT INTO Faculty VALUES ('DT', 130,N'Khoa Công Nghệ Thông Tin')
INSERT INTO Faculty VALUES ('TY', 131,N'Khoa Thú Y')
INSERT INTO Faculty VALUES ('NH', 132,N'Khoa Nông Học')
INSERT INTO Faculty VALUES ('CK', 133,N'Khoa Cơ Khí')
INSERT INTO Faculty VALUES ('AV', 134,N'Khoa Ngôn Ngữ Anh')
INSERT INTO Faculty VALUES ('LA', 135,N'Khoa Thiết kế cảnh quan')
INSERT INTO Faculty VALUES ('LN', 136,N'Khoa Lâm nghiệp')
INSERT INTO Faculty VALUES ('BV', 137,N'Khoa Bảo vệ thực vật')
INSERT INTO Faculty VALUES ('QL', 138,N'Khoa Quản lý đất đai')  

-- dữ liệu bảng ACCOUNT_has_role
insert into ACCOUNT_has_role VALUES (N'18130005',3) ;
insert into ACCOUNT_has_role VALUES (N'18130077',3) ;
insert into ACCOUNT_has_role VALUES (N'18130001',3) ;
insert into ACCOUNT_has_role VALUES (N'18130002',3) ;
insert into ACCOUNT_has_role VALUES (N'18130003',3) ;
insert into ACCOUNT_has_role VALUES (N'18130004',3) ;
insert into ACCOUNT_has_role VALUES (N'18130006',3) ;
insert into ACCOUNT_has_role VALUES (N'19130006',3) ;
insert into ACCOUNT_has_role VALUES (N'19130007',3) ;
insert into ACCOUNT_has_role VALUES (N'20130006',3) ;
insert into ACCOUNT_has_role VALUES (N'20130007',3) ;

insert into ACCOUNT_has_role VALUES (N'pdt',1) ;

insert into ACCOUNT_has_role VALUES (N'224',2) ;
insert into ACCOUNT_has_role VALUES (N'225',2) ;
insert into ACCOUNT_has_role VALUES (N'226',2) ;
insert into ACCOUNT_has_role VALUES (N'227',2) ;
insert into ACCOUNT_has_role VALUES (N'228',2) ;
insert into ACCOUNT_has_role VALUES (N'229',2) ;
insert into ACCOUNT_has_role VALUES (N'220',2) ;
insert into ACCOUNT_has_role VALUES (N'300',2) ;

----dữ liệu bảng ACCOUNT_detail
insert into ACCOUNT_detail VALUES (N'18130005','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'18130077','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'18130001','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'18130002','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'18130003','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'18130004','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'18130006','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'19130006','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'19130007','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'20130006','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'20130007','','','','','20/10/2018')

insert into ACCOUNT_detail VALUES (N'pdt','','','','','20/10/2018')

insert into ACCOUNT_detail VALUES (N'224','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'225','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'226','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'227','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'228','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'229','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'220','','','','','20/10/2018')
insert into ACCOUNT_detail VALUES (N'300','','','','','20/10/2018')
-- dữ liệu bảng Vertification Token
insert into Verification_Token VALUES (N'18130005',null,getdate())
insert into Verification_Token VALUES (N'18130077',null,getdate())
insert into Verification_Token VALUES (N'18130001',null,getdate())
insert into Verification_Token VALUES (N'18130002',null,getdate())
insert into Verification_Token VALUES (N'18130003',null,getdate())
insert into Verification_Token VALUES (N'18130004',null,getdate())
insert into Verification_Token VALUES (N'19130007',null,getdate())
insert into Verification_Token VALUES (N'20130006',null,getdate())
insert into Verification_Token VALUES (N'20130007',null,getdate())
insert into Verification_Token VALUES (N'224',null,getdate())
insert into Verification_Token VALUES (N'225',null,getdate())
insert into Verification_Token VALUES (N'226',null,getdate())
insert into Verification_Token VALUES (N'227',null,getdate())
insert into Verification_Token VALUES (N'228',null,getdate())
insert into Verification_Token VALUES (N'229',null,getdate())
insert into Verification_Token VALUES (N'220',null,getdate())
insert into Verification_Token VALUES (N'300',null,getdate())
insert into Verification_Token VALUES (N'pdt',null,getdate())
-- dữ liệu bảng Clazz
insert into Clazz Values(N'DH18DTA','DT',100,0)
insert into Clazz Values(N'DH19DTA','DT',100,0)
insert into Clazz Values(N'DH20DTA','DT',100,0)

--dữ liệu bảng Student
insert into Student Values(N'18130005',N'Đàm Văn Anh','DT','22/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130077',N'Ngô Minh Hiển','DT','22/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130001',N'Nguyễn Văn A','DT','22/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130002',N'Nguyễn Văn B','DT','22/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130003',N'Nguyễn Văn C','DT','22/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130004',N'Nguyễn Văn D','DT','22/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130006',N'Nguyễn Văn E','DT','22/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'19130006',N'Nguyễn Văn S','DT','20/10/2019',N'DH19DTA',136,0)
insert into Student Values(N'19130007',N'Nguyễn Văn S','DT','20/10/2019',N'DH19DTA',136,0)
insert into Student Values(N'20130006',N'Nguyễn Văn S','DT','20/10/2020',N'DH20DTA',136,0)
insert into Student Values(N'20130007',N'Nguyễn Văn S','DT','20/10/2020',N'DH20DTA',136,0)

-- insert into Professor
insert into Professor Values(N'224',N'A','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'225',N'B','DT','20/10/2000',N'Thạc Sĩ')
insert into Professor Values(N'226',N'C','DT','20/10/2000',N'thạc Sĩ')
insert into Professor Values(N'227',N'D','DT','20/10/2000',N'Phó Giáo sư')
insert into Professor Values(N'228',N'E','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'229',N'F','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'220',N'G','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'300',N'Van ANh','NH','20/10/2000',N'Tiến Sĩ');

-- insert into Semester
insert into Semester Values(N'2018_1','1/9/2018','31/1/2019',2018,1)
insert into Semester Values(N'2018_2','1/3/2019','30/6/2019',2018,2)
insert into Semester Values(N'2019_1','1/9/2019','31/1/2020',2019,1)
insert into Semester Values(N'2019_2','1/3/2020','30/6/2020',2019,2)
insert into Semester Values(N'2020_1','1/9/2020','31/1/2021',2020,1)
insert into Semester Values(N'2020_2','1/3/2021','30/6/2021',2020,2)
insert into Semester Values(N'2021_1','1/9/2021','31/1/2022',2021,1)
insert into Semester Values(N'2021_2','1/3/2022','30/6/2022',2021,2)

-- insert into Time_For_Course_Register
insert into Time_For_Course_Register Values(N'2018_1','6/1/2019','12/1/2019')
insert into Time_For_Course_Register Values(N'2018_2','3/5/2019','12/5/2019')
insert into Time_For_Course_Register Values(N'2019_1','6/1/2020','6/1/2020')
insert into Time_For_Course_Register Values(N'2019_2','3/5/2020','12/5/2020')
insert into Time_For_Course_Register Values(N'2020_1','6/1/2021','12/1/2021')
insert into Time_For_Course_Register Values(N'2020_2','3/5/2021','12/5/2021')
insert into Time_For_Course_Register Values(N'2021_1','6/1/2022','27/1/2022')
insert into Time_For_Course_Register Values(N'2021_2','30/3/2022','3/4/2022')


-- insert into course
-- đại cương
insert into Course Values(N'213603',null,N'Anh văn 1',4,1,1)
insert into Course Values(N'213604',null,N'Anh văn 2',3,2,2)
insert into Course Values(N'202108',null,N'Toán cao cấp A1',3,1,2)
insert into Course Values(N'202109',null,N'Toán cao cấp A2',3,1,1)
insert into Course Values(N'202110',null,N'Toán cao cấp A3',3,2,2)
insert into Course Values(N'200101',null,N'Triết học Mác Lênin',3,2,1)
insert into Course Values(N'200103',null,N'Chủ nghĩa xã hội khoa học',2,2,2)
insert into Course Values(N'200102',null,N'Kinh tế chính trị Mác-Lênin',2,2,2)
insert into Course Values(N'202622',null,N'Pháp luật đại cương',2,1,1)
insert into Course Values(N'202501',null,N'Giáo dục thể chất 1*',1,1,2)
insert into Course Values(N'200107',null,N'Tư tưởng Hồ Chí Minh',2,2,2)
insert into Course Values(N'214201',null,N'Nhập môn tin học',3,1,1)
insert into Course Values(N'202206',null,N'Vật lý 2',2,2,1)

-- chuyên nghành

--dt
insert into Course Values(N'214321','DT',N'Lập trình cơ bản',4,1,1)
insert into Course Values(N'214331','DT',N'Lập trình nâng cao',4,1,2)
insert into Course Values(N'214231','DT',N'Cấu trúc máy tính',2,1,2)
insert into Course Values(N'214242','DT',N'Nhập môn hệ điều hành',3,1,2)
insert into Course Values(N'208453','DT',N'Marketing căn bản',2,2,1)
insert into Course Values(N'202121','DT',N'Xác suất thống kê',3,2,1)
insert into Course Values(N'214241','DT',N'Mạng máy tính cơ bản',3,2,1)
insert into Course Values(N'214441','DT',N'Cấu trúc dữ liệu',4,2,1)
insert into Course Values(N'202620','DT',N'Kỹ năng giao tiếp',2,2,1)
insert into Course Values(N'214361','DT',N'Giao tiếp người máy',3,2,2)
insert into Course Values(N'214351','DT',N'Lý thuyết đồ thị',4,2,2)
insert into Course Values(N'200105','DT',N'Lịch sử đảng',2,2,2)
insert into Course Values(N'214442','DT',N'Nhập môn cơ sở dữ liệu',3,2,2)
insert into Course Values(N'214352','DT',N'Thiết kế hướng đối tượng',4,2,2)
insert into Course Values(N'214462','DT',N'Lập trình Web',4,3,1)
insert into Course Values(N'214463','DT',N'Nhập môn trí tuệ nhân tạo',4,3,1)
insert into Course Values(N'214372','DT',N'Lập trình.NET',4,3,1)
insert into Course Values(N'214353','DT',N'Đồ họa máy tính',3,3,1)
insert into Course Values(N'214386','DT',N'Lập trình PHP',4,3,1)
insert into Course Values(N'214451','DT',N'Hệ quản trị cơ sở dữ liệu',4,3,1)
insert into Course Values(N'214252','DT',N'Lập trình mạng',4,3,1)
insert into Course Values(N'214370','DT',N'Nhập môn CN phần mềm',4,3,2)
insert into Course Values(N'214471','DT',N'Hệ thống thông tin quản lý',3,3,4)
insert into Course Values(N'214282','DT',N'Mạng máy tính nâng cao',3,3,2)
insert into Course Values(N'214461','DT',N'Phân tích và thiết kế HTTT',4,3,2)
insert into Course Values(N'214492','DT',N'Máy học',3,3,2)
insert into Course Values(N'214273','DT',N'Lập trình mạng nâng cao',2,3,2)
insert into Course Values(N'214388','DT',N'Lập trình Front End',2,3,2)
insert into Course Values(N'214274','DT',N'Lập trình trên TB di động',2,3,2)
insert into Course Values(N'214483','DT',N'thương mại điên tử',2,4,1)
insert into Course Values(N'214485','DT',N'Data Mining',4,4,1)
insert into Course Values(N'214387','DT',N'Chuyên đề mã nguồn mở',2,4,1)
insert into Course Values(N'214491','DT',N'Data Warehouse',4,4,1)
insert into Course Values(N'214464','DT',N'An toàn và bảo mật hệ thống thông tin',2,4,1)
insert into Course Values(N'214286','DT',N'Chuyên đề Java',2,4,2)
insert into Course Values(N'214490','DT',N'Phân tích dữ liệu lớn',2,4,2)
insert into Course Values(N'214984','DT',N'Đồ án chuyên ngành',2,4,2)
insert into Course Values(N'214985','DT',N'Khóa luận tốt nghiệp',2,4,2)
insert into Course Values(N'214374','DT',N'Chuyên đề WEB',2,4,2)

-- insert into Course_Offering
insert into Course_Offering Values(N'1',N'213603','DH18DTA',80,0)
insert into Course_Offering Values(N'2',N'214201','DH18DTA',80,0)
insert into Course_Offering Values(N'3',N'202109','DH18DTA',80,0)
insert into Course_Offering Values(N'4',N'200102','DH18DTA',80,0)
insert into Course_Offering Values(N'5',N'214321','DH18DTA',80,0)
insert into Course_Offering Values(N'6',N'202206','DH18DTA',80,0)
insert into Course_Offering Values(N'7',N'202108','DH18DTA',80,0)
insert into Course_Offering Values(N'8',N'202501','DH18DTA',80,0)
insert into Course_Offering Values(N'9',N'200101','DH18DTA',80,0)
insert into Course_Offering Values(N'10',N'200103','DH18DTA',80,0)
insert into Course_Offering Values(N'11',N'213604','DH18DTA',80,0)
insert into Course_Offering Values(N'12',N'202110','DH18DTA',80,0)
insert into Course_Offering Values(N'13',N'214331','DH18DTA',80,0)
insert into Course_Offering Values(N'14',N'214231','DH18DTA',80,0)
insert into Course_Offering Values(N'15',N'214242','DH18DTA',80,0)
insert into Course_Offering Values(N'16',N'208453','DH18DTA',80,0)
insert into Course_Offering Values(N'17',N'202121','DH18DTA',80,0)
insert into Course_Offering Values(N'18',N'214241','DH18DTA',80,0)
insert into Course_Offering Values(N'19',N'214441','DH18DTA',80,0)
insert into Course_Offering Values(N'20',N'202622','DH18DTA',80,0)
insert into Course_Offering Values(N'21',N'202620','DH18DTA',80,0)
insert into Course_Offering Values(N'22',N'214361','DH18DTA',80,0)
insert into Course_Offering Values(N'23',N'214351','DH18DTA',80,0)
insert into Course_Offering Values(N'24',N'200105','DH18DTA',80,0)
insert into Course_Offering Values(N'25',N'214442','DH18DTA',80,0)
insert into Course_Offering Values(N'26',N'214352','DH18DTA',80,0)
insert into Course_Offering Values(N'27',N'214462','DH18DTA',80,0)
insert into Course_Offering Values(N'28',N'214463','DH18DTA',80,0)
insert into Course_Offering Values(N'29',N'214372','DH18DTA',80,0)
insert into Course_Offering Values(N'30',N'214353','DH18DTA',80,0)
insert into Course_Offering Values(N'31',N'214386','DH18DTA',80,0)
insert into Course_Offering Values(N'32',N'214451','DH18DTA',80,0)
insert into Course_Offering Values(N'33',N'200107','DH18DTA',80,0)
insert into Course_Offering Values(N'34',N'214252','DH18DTA',80,0)
insert into Course_Offering Values(N'35',N'214370','DH18DTA',80,0)
insert into Course_Offering Values(N'36',N'214471','DH18DTA',80,0)
insert into Course_Offering Values(N'37',N'214282','DH18DTA',80,0)
insert into Course_Offering Values(N'38',N'214461','DH18DTA',80,0)
insert into Course_Offering Values(N'39',N'214492','DH18DTA',80,0)
insert into Course_Offering Values(N'40',N'214273','DH18DTA',80,0)
insert into Course_Offering Values(N'41',N'214388','DH18DTA',80,0)
insert into Course_Offering Values(N'42',N'214274','DH18DTA',80,0)
insert into Course_Offering Values(N'43',N'214483','DH18DTA',80,0)
insert into Course_Offering Values(N'44',N'214485','DH18DTA',80,0)
insert into Course_Offering Values(N'45',N'214387','DH18DTA',80,0)
insert into Course_Offering Values(N'46',N'214491','DH18DTA',80,0)
insert into Course_Offering Values(N'47',N'214464','DH18DTA',80,0)
insert into Course_Offering Values(N'48',N'214286','DH18DTA',80,0)
insert into Course_Offering Values(N'49',N'214490','DH18DTA',80,0)
insert into Course_Offering Values(N'50',N'214984','DH18DTA',80,0)
insert into Course_Offering Values(N'51',N'214985','DH18DTA',80,0)
insert into Course_Offering Values(N'52',N'214374','DH18DTA',80,0)

-- insert into Schedule
insert into Schedule values(N'1',N'1',null,'LT',4,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'2',N'2',null,'LT',8,'20/10/2021','20/11/2021',N'Cẩm Tú',1,4)
insert into Schedule values(N'3',N'3',null,'LT',5,'20/10/2021','20/11/2021',N'Cẩm Tú',1,4)
insert into Schedule values(N'4',N'4',null,'LT',7,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'5',N'5',null,'LT',5,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'6',N'5',null,'TH',3,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'7',N'6',null,'LT',4,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'8',N'7',null,'LT',8,'20/10/2021','20/11/2021',N'Cẩm Tú',1,4)
insert into Schedule values(N'9',N'8',null,'LT',5,'20/10/2021','20/11/2021',N'Cẩm Tú',1,4)
insert into Schedule values(N'10',N'9',null,'LT',7,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'11',N'10',null,'LT',5,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'12',N'11',null,'TH',3,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'13',N'12',null,'LT',4,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'14',N'13',null,'LT',8,'20/10/2021','20/11/2021',N'Cẩm Tú',1,4)
insert into Schedule values(N'15',N'13',null,'TH',5,'20/10/2021','20/11/2021',N'Cẩm Tú',1,4)
insert into Schedule values(N'16',N'15',null,'LT',7,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'17',N'16',null,'LT',5,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'18',N'17',null,'TH',3,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'19',N'19',null,'LT',5,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'20',N'19',null,'TH',5,'20/10/2021','20/11/2021',N'Rạng Đông',2,4)
insert into Schedule values(N'21',N'20',null,'LT',3,'20/10/2021','20/11/2021',N'Rạng Đông',4,4)
insert into Schedule values(N'22',N'21',null,'LT',3,'20/10/2021','20/11/2021',N'Rạng Đông',3,4)
insert into Schedule values(N'23',N'22',null,'LT',3,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'24',N'23',null,'LT',4,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'25',N'24',null,'LT',4,'20/10/2021','20/11/2021',N'Rạng Đông',2,4)
insert into Schedule values(N'26',N'25',null,'LT',4,'20/10/2021','20/11/2021',N'Rạng Đông',3,4)
insert into Schedule values(N'27',N'26',null,'LT',5,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'28',N'27',null,'LT',5,'20/10/2021','20/11/2021',N'Rạng Đông',2,4)
insert into Schedule values(N'29',N'28',null,'LT',5,'20/10/2021','20/11/2021',N'Rạng Đông',3,4)
insert into Schedule values(N'30',N'29',null,'LT',6,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'31',N'30',null,'LT',6,'20/10/2021','20/11/2021',N'Rạng Đông',2,4)
insert into Schedule values(N'32',N'31',null,'LT',6,'20/10/2021','20/11/2021',N'Rạng Đông',3,4)
insert into Schedule values(N'33',N'32',null,'LT',7,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'34',N'33',null,'LT',7,'20/10/2021','20/11/2021',N'Rạng Đông',2,4)
insert into Schedule values(N'35',N'34',null,'LT',7,'20/10/2021','20/11/2021',N'Rạng Đông',3,4)
insert into Schedule values(N'36',N'35',null,'LT',2,'20/10/2021','20/11/2021',N'Rạng Đông',4,4)
insert into Schedule values(N'37',N'36',null,'LT',3,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'38',N'37',null,'LT',3,'20/10/2021','20/11/2021',N'Rạng Đông',2,4)
insert into Schedule values(N'39',N'38',null,'LT',3,'20/10/2021','20/11/2021',N'Rạng Đông',3,4)
insert into Schedule values(N'40',N'39',null,'LT',2,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
insert into Schedule values(N'41',N'40',null,'LT',2,'20/10/2021','20/11/2021',N'Rạng Đông',2,4)
insert into Schedule values(N'42',N'40',null,'TH',2,'20/10/2021','20/11/2021',N'Rạng Đông',2,4)
insert into Schedule values(N'43',N'36',null,'TH',2,'20/10/2021','20/11/2021',N'Rạng Đông',2,4)
insert into Schedule values(N'44',N'38',null,'TH',2,'20/10/2021','20/11/2021',N'Rạng Đông',2,4)
insert into Schedule values(N'45',N'43',null,'TH',2,'20/10/2021','10/12/2021',N'Máy 2',5,4)
insert into Schedule values(N'46',N'43',null,'LT',2,'20/10/2021','10/12/2021',N'Rạng Đông',4,4)
insert into Schedule values(N'47',N'44',null,'TH',2,'20/10/2021','10/12/2021',N'Máy 3',2,4)
insert into Schedule values(N'48',N'44',null,'LT',2,'20/10/2021','10/12/2021',N'Rạng Đông',3,4)
insert into Schedule values(N'49',N'45',null,'TH',3,'20/10/2021','10/12/2021',N'Máy 1',6,4)
insert into Schedule values(N'50',N'45',null,'LT',4,'20/10/2021','10/12/2021',N'Rạng Đông',6,1)
insert into Schedule values(N'51',N'46',null,'TH',2,'20/10/2021','10/12/2021',N'Máy 1',6,7)
insert into Schedule values(N'52',N'46',null,'LT',2,'20/10/2021','10/12/2021',N'Rạng Đông',7,1)
insert into Schedule values(N'53',N'47',null,'TH',2,'20/10/2021','10/12/2021',N'Máy 1',2,7)
insert into Schedule values(N'54',N'47',null,'LT',2,'20/10/2021','10/12/2021',N'Rạng Đông',3,1)
insert into Schedule values(N'55',N'48',null,'TH',2,'20/10/2021','10/12/2021',N'Máy 1',3,4)
insert into Schedule values(N'56',N'48',null,'LT',2,'20/10/2021','10/12/2021',N'Rạng Đông',4,1)
insert into Schedule values(N'57',N'49',null,'TH',2,'20/10/2021','10/12/2021',N'Máy 1',4,4)
insert into Schedule values(N'58',N'49',null,'LT',2,'20/10/2021','10/12/2021',N'Rạng Đông',5,1)
insert into Schedule values(N'59',N'50',null,'TH',2,'20/10/2021','10/12/2021',N'Máy 1',5,4)
insert into Schedule values(N'60',N'50',null,'LT',2,'20/10/2021','10/12/2021',N'Rạng Đông',4,7)
insert into Schedule values(N'61',N'51',null,'TH',2,'20/10/2021','10/12/2021',N'Máy 1',4,10)
insert into Schedule values(N'62',N'51',null,'LT',2,'20/10/2021','10/12/2021',N'Rạng Đông',4,12)
insert into Schedule values(N'63',N'52',null,'TH',2,'20/10/2021','10/12/2021',N'Máy 1',4,10)
insert into Schedule values(N'64',N'52',null,'LT',2,'20/10/2021','10/12/2021',N'Rạng Đông',3,10)

-- insert into Student_Schedule

insert into Student_Schedule values('2019_2',N'28',N'18130006')
insert into Student_Schedule values('2019_2',N'29',N'18130006')
insert into Student_Schedule values('2019_2',N'30',N'18130006')
insert into Student_Schedule values('2019_2',N'31',N'18130006')
insert into Student_Schedule values('2019_2',N'32',N'18130006')
--insert into Student_Schedule values('2020_1',N'36',N'18130006')
--insert into Student_Schedule values('2020_1',N'37',N'18130006')
--insert into Student_Schedule values('2020_1',N'38',N'18130006')
--insert into Student_Schedule values('2020_1',N'39',N'18130006')
--insert into Student_Schedule values('2020_1',N'40',N'18130006')
insert into Student_Schedule values('2020_2',N'36',N'18130006')
insert into Student_Schedule values('2020_2',N'37',N'18130006')
insert into Student_Schedule values('2020_2',N'38',N'18130006')
insert into Student_Schedule values('2020_2',N'39',N'18130006')
insert into Student_Schedule values('2020_2',N'40',N'18130006')
insert into Student_Schedule values('2020_2',N'36',N'18130005')
insert into Student_Schedule values('2020_2',N'37',N'18130005')
insert into Student_Schedule values('2020_2',N'38',N'18130005')
insert into Student_Schedule values('2020_2',N'39',N'18130005')
insert into Student_Schedule values('2020_2',N'40',N'18130005')
insert into Student_Schedule values('2021_1',N'40',N'18130005')
insert into Student_Schedule values('2021_1',N'41',N'18130005')

-- insert into Student_ScheduleR
insert into Student_Schedule_R values('2020_1',N'30',N'18130006')
insert into Student_Schedule_R values('2020_1',N'31',N'18130006')
insert into Student_Schedule_R values('2020_1',N'32',N'18130006')
insert into Student_Schedule_R values('2020_1',N'33',N'18130006')
insert into Student_Schedule_R values('2020_1',N'34',N'18130006')
insert into Student_Schedule_R values('2020_1',N'35',N'18130006')
insert into Student_Schedule_R values('2020_2',N'36',N'18130006')
insert into Student_Schedule_R values('2020_2',N'37',N'18130006')
insert into Student_Schedule_R values('2020_2',N'38',N'18130006')
insert into Student_Schedule_R values('2020_2',N'39',N'18130006')
insert into Student_Schedule_R values('2020_2',N'40',N'18130006')
insert into Student_Schedule_R values('2020_2',N'36',N'18130005')
insert into Student_Schedule_R values('2020_2',N'37',N'18130005')
insert into Student_Schedule_R values('2020_2',N'38',N'18130005')
insert into Student_Schedule_R values('2020_2',N'39',N'18130005')
insert into Student_Schedule_R values('2020_2',N'41',N'18130005')
insert into Student_Schedule_R values('2020_2',N'42',N'18130005')

-- insert into Professor_Schedule
insert into Professor_Schedule values('2019_2',N'28',N'224')
insert into Professor_Schedule values('2019_2',N'29',N'224')
insert into Professor_Schedule values('2019_2',N'30',N'224')
insert into Professor_Schedule values('2019_2',N'31',N'224')
insert into Professor_Schedule values('2019_2',N'32',N'224')
--insert into Professor_Schedule values('2020_1',N'36',N'224')
--insert into Professor_Schedule values('2020_1',N'37',N'224')
--insert into Professor_Schedule values('2020_1',N'38',N'224')
--insert into Professor_Schedule values('2020_1',N'39',N'224')
--insert into Professor_Schedule values('2020_1',N'40',N'224')
insert into Professor_Schedule values('2020_2',N'36',N'224')
insert into Professor_Schedule values('2020_2',N'37',N'224')
insert into Professor_Schedule values('2020_2',N'38',N'224')
insert into Professor_Schedule values('2020_2',N'39',N'224')
insert into Professor_Schedule values('2020_2',N'40',N'224')

-- insert into front_Sub
insert into front_Sub values(N'214331',N'214321')
insert into front_Sub values(N'214441',N'214331')

insert into Sub_Pass values('2021_1',N'214492',N'18130005',6.5,2.5,N'C')

insert into semester_Result values('2020_2',N'18130005',6.37,2.23,4)
insert into semester_Result values('2021_1',N'18130005',6.0,2.0,4)
insert into semester_Result values('2021_2',N'18130005',7.6,2.5,16)

--test 

select * from ACCOUNT
-- GO

-- SELECT sd.*
-- FROM Time_Table_St('19130006') sd
-- ORDER BY sd.ID_Course_Offering;

-- --
-- SELECT *
-- FROM get_Semester_Reuslt('18130006', '2021_1');
-- -- tính điểm TB từ semester_Result
-- SELECT SUM(gr.Score * gr.Course_certificate) / SUM(gr.Course_certificate) Diem_TB
-- FROM get_Semester_Reuslt('18130005', '2_2018') gr;
-- -- tính điểm TB từ semester_Result(hệ 4)
-- SELECT SUM(gr.Score_System_4 * gr.Course_certificate) / SUM(gr.Course_certificate) Diem_TB_He_4
-- FROM get_Semester_Reuslt('18130005', '2_2018') gr;
-- -- lấy tổng tín chỉ đã đạt trong học kỳ(ko lấy môn dưới 4.0)
-- SELECT SUM(gr.Course_certificate) so_TC
-- FROM get_Semester_Reuslt('18130005', '2_2018') gr
-- WHERE gr.Score > 4.0;
-- -- tổng tín chỉ đã đăng ký trong học kỳ
-- SELECT SUM(gr.Course_certificate) so_TC
-- FROM get_Semester_Reuslt('18130005', '2_2018') gr;
-- --

-- GO
-- --
-- SELECT *
-- FROM get_Semester_Reuslt('18130006', '2021_1');
-- -- tính điểm TB từ semester_Result
-- SELECT SUM(gr.Score * gr.Course_certificate) / SUM(gr.Course_certificate) Diem_TB
-- FROM get_Semester_Reuslt('18130005', '2_2018') gr;
-- -- tính điểm TB từ semester_Result(hệ 4)
-- SELECT SUM(gr.Score_System_4 * gr.Course_certificate) / SUM(gr.Course_certificate) Diem_TB_He_4
-- FROM get_Semester_Reuslt('18130005', '2_2018') gr;
-- -- lấy tổng tín chỉ đã đạt trong học kỳ(ko lấy môn dưới 4.0)
-- SELECT SUM(gr.Course_certificate) so_TC
-- FROM get_Semester_Reuslt('18130005', '2_2018') gr
-- WHERE gr.Score > 4.0;
-- -- tổng tín chỉ đã đăng ký trong học kỳ
-- SELECT SUM(gr.Course_certificate) so_TC
-- FROM get_Semester_Reuslt('18130005', '2_2018') gr;
-- --

-- -- tạo function Final_Result(éo biết tính như nào)
-- SELECT * FROM Clazz
-- SELECT * FROM Student DELETE Student WHERE ID_Student LIKE '18135%'
-- DELETE Clazz WHERE Clazz_code LIKE 'DH18TY%'
-- SELECT * FROM Course_Offering
-- UPDATE dbo.Course_Offering SET ID_Course = '213603',Clazz_code = 'DH18DTA',Max_Size = 80,Current_Size = 0 WHERE ID_Course_Offering = '1'
-- ---
-- SELECT sa.ID_Schedule
-- FROM Sub_Available_ST('18130005') sa JOIN
-- Time_Table_St('18130005') tt ON sa.ID_Schedule = tt.ID_Schedule
-- WHERE sa.ID_Schedule = '12';
-- --- Nhập điểm cho giáo viên
-- SELECT DISTINCT c.ID_Course,
--                 c.Name_Course,
--                 pr.ID_Semester
-- FROM Professor_Schedule pr JOIN
-- Schedule sc ON pr.ID_Schedule = sc.ID_Schedule JOIN
-- Course_Offering co ON sc.ID_Course_Offering = co.ID_Course_Offering JOIN
-- Course c ON co.ID_Course = c.ID_Course
-- WHERE pr.ID_Semester = (SELECT
--   s.ID_Semester
-- FROM Semester s
-- WHERE GETDATE() BETWEEN s.start_Date AND s.end_Date)
-- AND pr.ID_Professor = '224';

-- SELECT st.ID_Student,
--        s.Student_Name
-- FROM Student_Schedule st JOIN
-- Schedule sc ON st.ID_Schedule = sc.ID_Schedule JOIN
-- Course_Offering co ON sc.ID_Course_Offering = co.ID_Course_Offering JOIN
-- Course c ON co.ID_Course = c.ID_Course JOIN
-- Student s ON st.ID_Student = s.ID_Student
-- WHERE c.ID_Course = '214282'
-- AND st.ID_Semester = '2020_2';


-- SELECT sp.Score
-- FROM Sub_Pass sp
-- WHERE sp.ID_Student = '18130005'
-- AND sp.ID_Course = '214282'
-- AND sp.ID_Semester = '2020_2';

-- ------ xét nó có trong cơ sỡ dữ liệu hay chưa
-- DELETE FROM Student_Schedule_R;
-- SELECT strr.*
-- FROM Student_Schedule_R strr
-- WHERE strr.ID_Semester = '2020_2'
-- AND strr.ID_Student = '18130005'
-- AND strr.ID_Schedule = '36'

-- SELECT st.ID_Schedule
-- FROM Student_Schedule st
-- WHERE st.ID_Semester = '2020_2'
-- AND st.ID_Student = '18130005'

-- SELECT COUNT(DISTINCT c.ID_Course) AS dem
-- FROM Student_Schedule st JOIN
-- Schedule sc ON st.ID_Schedule = sc.ID_Schedule JOIN
-- Course_Offering co ON sc.ID_Course_Offering = co.ID_Course_Offering JOIN
-- Course c ON co.ID_Course = c.ID_Course
-- WHERE st.ID_Semester = '2020_2'
-- AND st.ID_Student = '18130005'


select *
from Sub_Pass
where ID_Semester = '2018_1'
ORDER BY ID_Student
select *
from Final_Result
select *
from semester_Result
where ID_Semester = '2018_1'
ORDER BY ID_Student
SELECT *
FROM Sub_Pass sp
WHERE  sp.ID_Student = '18130005'
select *
from Student