CREATE DATABASE Course_Registration
GO
USE Course_Registration
SET DATEFORMAT DMY
GO

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
  ID_ACCOUNT nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT) ON DELETE CASCADE,
  [id] int NOT NULL FOREIGN KEY REFERENCES Role ([id]),
  PRIMARY KEY (ID_ACCOUNT,[id])
)
CREATE TABLE ACCOUNT_Detail
(
  ID_ACCOUNT nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT) ON DELETE CASCADE,
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
  [account_id] nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT) ON DELETE CASCADE,
  PRIMARY KEY ([id])
)
CREATE TABLE Verification_Token
(
  ID_ACCOUNT nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT) ON DELETE CASCADE,
  [verification_token_content] [nvarchar](max) NULL,
  [verified] [datetime2](7) NULL,
  PRIMARY KEY (ID_ACCOUNT)
)
CREATE TABLE Reset_Token
(
  ID_ACCOUNT nvarchar(50) NOT NULL FOREIGN KEY REFERENCES ACCOUNT (ID_ACCOUNT) ON DELETE CASCADE,
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
  ID_Faculty nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Faculty (ID_Faculty) ON DELETE CASCADE,
  -- Khóa học của trường (VD DH18DTA thì khóa của trường là 18 vì mình nộp hồ sơ năm 2018)
  -- Cách tính khóa học lấy năm nộp hồ sơ (trong đây là năm tạo tài khoản) trừ cho năm mốc (năm 2000)
  Create_date smalldatetime NOT NULL,
  -- Clazz code là mã lớp (VD DH18DTA)
  Clazz_code nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Clazz (Clazz_code) ON DELETE CASCADE,
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
  img_url VARCHAR  NOT NULL,
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
  years int,
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
CREATE TABLE Student_Schedule_F
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

CREATE TABLE Date_Exam
(
	id int IDENTITY(1,1) PRIMARY KEY,
	ID_Semester nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Semester (ID_Semester),
	ID_Schedule nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Schedule (ID_Schedule),
	group_Exam nvarchar(50) ,
	seats smallint  not null,
)


CREATE TABLE Course_Progress
(
	id int IDENTITY(1,1) PRIMARY KEY,
	ID_Faculty nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Faculty (ID_Faculty),
	ID_Course nvarchar(50) NOT NULL FOREIGN KEY REFERENCES Course (ID_Course),
	-- nam hoc bat dau
	number_year int,
	optional BIT
)

-- function
go
-- lay ra ctdt va check da hoc qua chua
create FUNCTION get_Course_Progress_Table_ST (@ID_ACCOUNT varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT cp.*, 
  CASE WHEN cp.ID_Course in (select sp.ID_Course from Sub_Pass sp where sp.ID_Student = @ID_ACCOUNT and sp.Score >= 4) THEN 1 ELSE 0 END AS Pass
  from Course_Progress cp
GO

-- Thời khóa biểu cho giáo viên
CREATE FUNCTION Time_Table_Pr (@ID_Professor varchar(50),@ID_Semester varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT sd.*
FROM Schedule sd JOIN
  Course_Offering co ON sd.ID_Course_Offering = co.ID_Course_Offering JOIN
  Professor_Schedule prc ON prc.ID_Schedule = sd.ID_Schedule
WHERE prc.ID_Professor = @ID_Professor
  AND sd.ID_Schedule >= 0
  AND prc.ID_Semester = @ID_Semester



GO
-- thời khóa biểu có học sinh , thời khóa biểu của giáo viên chỉ cần gọi schdule
CREATE FUNCTION Time_Table_St (@ID_ACCOUNT varchar(50),@ID_Semester varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT sd.ID_Schedule,
  sd.ID_Course_Offering
FROM Schedule sd JOIN
	  Course_Offering co ON sd.ID_Course_Offering = co.ID_Course_Offering JOIN
	  Student_Schedule stc ON stc.ID_Schedule = sd.ID_Schedule
WHERE stc.ID_Student = @ID_ACCOUNT
  AND sd.ID_Schedule >= 0
  AND stc.ID_Semester = @ID_Semester

GO

-- Xem lich thi cua hoc sinh
CREATE FUNCTION Date_Exam_ST (@ID_ACCOUNT varchar(50),@ID_Semester varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT de.* FROM Date_Exam de 
  where de.ID_Schedule in (select ID_Schedule from Schedule where ID_Course_Offering in  (select tts.ID_Course_Offering FROM Time_Table_St (@ID_ACCOUNT,@ID_Semester ) tts) )
  and de.ID_Semester = @ID_Semester
GO


go

create FUNCTION sub_Passed (@ID_Course_B nvarchar(50), @ID_ACCOUNT nvarchar(50))
RETURNS nvarchar(50)
AS
BEGIN
  DECLARE @ID_Course_B1 nvarchar(50)
  (SELECT @ID_Course_B1 = fs.ID_Course_B FROM front_Sub fs WHERE fs.ID_Course_B = @ID_Course_B 
														   AND fs.ID_Course = CASE WHEN (SELECT ID_Course
																						FROM Sub_Pass
																						WHERE ID_Course = fs.ID_Course
																						AND ID_Student = @ID_ACCOUNT
																						AND Score >= 4) IS NOT NULL THEN fs.ID_Course ELSE NULL END)
  RETURN @ID_Course_B1;
END
GO

go
-- những môn sẽ hiển thị khi nhấn đăng k
-- những môn có thể đăng ký của giáo viên thì chọn những môn nào trong bảng schedule có chỗ id pr là nullý môn học
create FUNCTION Sub_Available_ST (@ID_ACCOUNT varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT sc.ID_Schedule , co.ID_Course ,c.Name_Course
FROM Course_Offering co JOIN
  Course c ON c.ID_Course = co.ID_Course JOIN
  (select cp.ID_Course from Course_Progress cp where cp.number_year =  SUBSTRING(@ID_ACCOUNT,1,2)) cp ON c.ID_Course = cp.ID_Course JOIN
  Schedule sc ON sc.ID_Course_Offering = co.ID_Course_Offering

WHERE	(c.ID_Faculty is null or c.ID_Faculty  in  (SELECT ID_Faculty FROM Student WHERE ID_Student = @ID_ACCOUNT))
		AND sc.ID_Schedule >=0 
		AND c.years <= CASE WHEN c.years IS NULL THEN c.years 
			ELSE (SELECT (YEAR(GETDATE()) - YEAR(Create_date))
				FROM Student
				WHERE ID_Student = @ID_ACCOUNT) END
		AND c.number_S = CASE WHEN c.number_S = 0 THEN c.number_S
			ELSE (SELECT number_S
				FROM Semester
				WHERE ID_Semester IN (SELECT ID_Semester
										FROM Semester
										WHERE GETDATE() BETWEEN start_Date AND end_Date)) END
		AND c.ID_Course =CASE WHEN (SELECT top 1 ID_Course_B
									FROM front_Sub
									WHERE ID_Course_B = c.ID_Course) IS NULL THEN c.ID_Course
									WHEN [dbo].sub_Passed(c.ID_Course, @ID_ACCOUNT) IS NULL THEN NULL
									ELSE [dbo].sub_Passed(c.ID_Course, @ID_ACCOUNT) END
		--AND CAST(SUBSTRING(co.Clazz_code,3,2) AS int) = (( YEAR( GETDATE() ) % 100 )) + 1 - (c.number_S - 1) - c.years
GO

select * from Sub_Available_ST('18130005');

-- bảng này là bảng check khi nhấn vào ô chọn môn học nếu trùng giờ trùng ngày , trùng môn nếu rỗng thì ko đk được
GO

CREATE FUNCTION check_Start_Slot_TeachDay (@ID_ACCOUNT varchar(50), @Start_Slot varchar(50), @Teaching_Day varchar(50))
RETURNS TABLE
AS
  RETURN
  SELECT sc.Start_Slot
FROM Schedule sc JOIN
  Student_Schedule_F stc ON sc.ID_Schedule = stc.ID_Schedule
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
  Student_Schedule_F stc ON sc.ID_Schedule = stc.ID_Schedule JOIN
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
INSERT INTO Faculty VALUES ('DC', 000,N'Đại Cương')
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
insert into Professor Values(N'224',N'A','','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'225',N'B','','DT','20/10/2000',N'Thạc Sĩ')
insert into Professor Values(N'226',N'C','','DT','20/10/2000',N'thạc Sĩ')
insert into Professor Values(N'227',N'D','','DT','20/10/2000',N'Phó Giáo sư')
insert into Professor Values(N'228',N'E','','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'229',N'F','','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'220',N'G','','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'300',N'Van ANh','','NH','20/10/2000',N'Tiến Sĩ');

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

insert into Course Values(N'202501',null,N'Giáo dục thể chất 1*',1,1,1);
insert into Course Values(N'214201',null,N'Nhập môn tin học',3,1,1);
insert into Course Values(N'202109',null,N'Toán cao cấp A2',3,1,1);
insert into Course Values(N'202108',null,N'Toán cao cấp A1',3,1,1);
insert into Course Values(N'200101',null,N'Triết học Mác Lênin',3,1,1);
insert into Course Values(N'200102',null,N'Kinh tế chính trị Mác- Lênin',2,1,1);
insert into Course Values(N'213603',null,N'Anh văn 1',4,1,1);
insert into Course Values(N'202206',null,N'Vật lý 2',2,1,1);
insert into Course Values(N'200202',null,N'Quân sự (thực hành)*',3,1,2);
insert into Course Values(N'202502',null,N'Giáo dục thể chất 2*',1,1,2);
insert into Course Values(N'200103',null,N'Chủ nghĩa xã hội khoa học',2,1,2);
insert into Course Values(N'213604',null,N'Anh văn 2',3,1,2);
insert into Course Values(N'200201',null,N'Quân sự 1 (lý thuyết)*',3,1,2);
insert into Course Values(N'202110',null,N'Toán cao cấp A3',3,1,2);
insert into Course Values(N'202622',null,N'Pháp luật đại cương',2,2,1);
insert into Course Values(N'202121',null,N'Xác suất thống kê',3,2,1);
insert into Course Values(N'200105',null,N'Lịch sử Đảng Cộng sản Việt Nam',2,2,2);
insert into Course Values(N'200107',null,N'Tư tưởng Hồ Chí Minh',2,3,1);

-- chuyên nghành



insert into Course Values(N'214321','DT',N'Lập trình cơ bản',4,1,1);
insert into Course Values(N'214331','DT',N'Lập trình nâng cao',4,1,2);
insert into Course Values(N'214242','DT',N'Nhập môn hệ điều hành',3,1,2);
insert into Course Values(N'214231','DT',N'Cấu trúc máy tính',2,1,2);
insert into Course Values(N'208453','DT',N'Marketing căn bản',2,2,1);
insert into Course Values(N'214441','DT',N'Cấu trúc dữ liệu',4,2,1);
insert into Course Values(N'214241','DT',N'Mạng máy tính cơ bản',3,2,1);
insert into Course Values(N'202620','DT',N'Kỹ năng giao tiếp',2,2,1);
insert into Course Values(N'214251','DT',N'Hệ điều hành nâng cao',3,2,1);
insert into Course Values(N'214352','DT',N'Thiết kế hướng đối tượng',4,2,2);
insert into Course Values(N'214442','DT',N'Nhập môn cơ sở dữ liệu',4,2,2);
insert into Course Values(N'214351','DT',N'Lý thuyết đồ thị',4,2,2);
insert into Course Values(N'214361','DT',N'Giao tiếp người _máy',3,2,2);
insert into Course Values(N'214463','DT',N'Nhập môn trí tuệ nhân tạo',4,3,1);
insert into Course Values(N'214252','DT',N'Lập trình mạng',4,3,1);
insert into Course Values(N'214462','DT',N'Lập trình Web',4,3,1);
insert into Course Values(N'214353','DT',N'Đồ họa máy tính',3,3,1);
insert into Course Values(N'214372','DT',N'Lập trình .NET',4,3,1);
insert into Course Values(N'214451','DT',N'Hệ quản trị cơ sở dữ liệu',3,3,1);
insert into Course Values(N'214386','DT',N'Lập trình PHP',4,3,1);
insert into Course Values(N'214282','DT',N'Mạng máy tính nâng cao',4,3,2);
insert into Course Values(N'214492','DT',N'Máy học',4,3,2);
insert into Course Values(N'214471','DT',N'Hệ thống thông tin quản lý',3,3,2);
insert into Course Values(N'214461','DT',N'Phân tích và thiết kế hệ thống thông tin',4,3,2);
insert into Course Values(N'214370','DT',N'Nhập môn công nghệ phần mềm',4,3,2);
insert into Course Values(N'214274','DT',N'Lập trình trên thiết bị di động',3,3,2);
insert into Course Values(N'214388','DT',N'Lập trình Front End',4,3,2);
insert into Course Values(N'214273','DT',N'Lập trình mạng nâng cao',4,3,2);
insert into Course Values(N'214387','DT',N'Chuyên đề mã nguồn mở',3,4,1);
insert into Course Values(N'214485','DT',N'Data Mining',4,4,1);
insert into Course Values(N'214483','DT',N'Thương mại điện tử',3,4,1);
insert into Course Values(N'214383','DT',N'Quản lý dự án phần mềm',3,4,1);
insert into Course Values(N'214289','DT',N'Giải pháp phần mềm chính phủ điện tử',4,4,1);
insert into Course Values(N'214290','DT',N'IoT',3,4,1);
insert into Course Values(N'214379','DT',N'Đảm bảo chất lượng và kiểm thử phần mềm',4,4,1);
insert into Course Values(N'214271','DT',N'Quản trị mạng',3,4,1);
insert into Course Values(N'214464','DT',N'An toàn và bảo mật hệ thống thông tin',3,4,1);
insert into Course Values(N'214491','DT',N'Data Warehouse',3,4,1);
insert into Course Values(N'214465','DT',N'Hệ thống thông tin địa lý ứng dụng',3,4,1);
insert into Course Values(N'214292','DT',N'An ninh mạng',3,4,1);
insert into Course Values(N'214286','DT',N'Chuyên đề Java',4,4,2);
insert into Course Values(N'214285','DT',N'Giải pháp mạng cho doanh nghiệp',4,4,2);
insert into Course Values(N'214291','DT',N'Xử lý ảnh và thị giác máy tính',4,4,2);
insert into Course Values(N'214490','DT',N'Phân tích dữ liệu lớn',4,4,2);
insert into Course Values(N'214985','DT',N'Khóa luận tốt nghiệp',10,4,2);
insert into Course Values(N'214984','DT',N'Đồ án chuyên ngành',2,4,2);
insert into Course Values(N'214374','DT',N'Chuyên đề WEB',4,4,2);
--dt

-- insert into Course_Offering
insert into Course_Offering Values(N'0',N'202501','DH18DTA',80,0)
insert into Course_Offering Values(N'1',N'214201','DH18DTA',80,0)
insert into Course_Offering Values(N'2',N'202109','DH18DTA',80,0)
insert into Course_Offering Values(N'3',N'202108','DH18DTA',80,0)
insert into Course_Offering Values(N'4',N'200101','DH18DTA',80,0)
insert into Course_Offering Values(N'5',N'200102','DH18DTA',80,0)
insert into Course_Offering Values(N'6',N'214321','DH18DTA',80,0)
insert into Course_Offering Values(N'7',N'213603','DH18DTA',80,0)
insert into Course_Offering Values(N'8',N'202206','DH18DTA',80,0)
insert into Course_Offering Values(N'9',N'200202','DH18DTA',80,0)
insert into Course_Offering Values(N'10',N'202502','DH18DTA',80,0)
insert into Course_Offering Values(N'11',N'200103','DH18DTA',80,0)
insert into Course_Offering Values(N'12',N'213604','DH18DTA',80,0)
insert into Course_Offering Values(N'13',N'200201','DH18DTA',80,0)
insert into Course_Offering Values(N'14',N'214331','DH18DTA',80,0)
insert into Course_Offering Values(N'15',N'202110','DH18DTA',80,0)
insert into Course_Offering Values(N'16',N'214242','DH18DTA',80,0)
insert into Course_Offering Values(N'17',N'214231','DH18DTA',80,0)
insert into Course_Offering Values(N'18',N'202622','DH18DTA',80,0)
insert into Course_Offering Values(N'19',N'208453','DH18DTA',80,0)
insert into Course_Offering Values(N'20',N'214441','DH18DTA',80,0)
insert into Course_Offering Values(N'21',N'214241','DH18DTA',80,0)
insert into Course_Offering Values(N'22',N'202620','DH18DTA',80,0)
insert into Course_Offering Values(N'23',N'214251','DH18DTA',80,0)
insert into Course_Offering Values(N'24',N'202121','DH18DTA',80,0)
insert into Course_Offering Values(N'25',N'200105','DH18DTA',80,0)
insert into Course_Offering Values(N'26',N'214352','DH18DTA',80,0)
insert into Course_Offering Values(N'27',N'214442','DH18DTA',80,0)
insert into Course_Offering Values(N'28',N'214351','DH18DTA',80,0)
insert into Course_Offering Values(N'29',N'214361','DH18DTA',80,0)
insert into Course_Offering Values(N'30',N'214463','DH18DTA',80,0)
insert into Course_Offering Values(N'31',N'214252','DH18DTA',80,0)
insert into Course_Offering Values(N'32',N'200107','DH18DTA',80,0)
insert into Course_Offering Values(N'33',N'214462','DH18DTA',80,0)
insert into Course_Offering Values(N'34',N'214353','DH18DTA',80,0)
insert into Course_Offering Values(N'35',N'214372','DH18DTA',80,0)
insert into Course_Offering Values(N'36',N'214451','DH18DTA',80,0)
insert into Course_Offering Values(N'37',N'214386','DH18DTA',80,0)
insert into Course_Offering Values(N'38',N'214282','DH18DTA',80,0)
insert into Course_Offering Values(N'39',N'214492','DH18DTA',80,0)
insert into Course_Offering Values(N'40',N'214471','DH18DTA',80,0)
insert into Course_Offering Values(N'41',N'214461','DH18DTA',80,0)
insert into Course_Offering Values(N'42',N'214370','DH18DTA',80,0)
insert into Course_Offering Values(N'43',N'214274','DH18DTA',80,0)
insert into Course_Offering Values(N'44',N'214388','DH18DTA',80,0)
insert into Course_Offering Values(N'45',N'214273','DH18DTA',80,0)
insert into Course_Offering Values(N'46',N'214387','DH18DTA',80,0)
insert into Course_Offering Values(N'47',N'214485','DH18DTA',80,0)
insert into Course_Offering Values(N'48',N'214483','DH18DTA',80,0)
insert into Course_Offering Values(N'49',N'214383','DH18DTA',80,0)
insert into Course_Offering Values(N'50',N'214289','DH18DTA',80,0)
insert into Course_Offering Values(N'51',N'214290','DH18DTA',80,0)
insert into Course_Offering Values(N'52',N'214379','DH18DTA',80,0)
insert into Course_Offering Values(N'53',N'214271','DH18DTA',80,0)
insert into Course_Offering Values(N'54',N'214464','DH18DTA',80,0)
insert into Course_Offering Values(N'55',N'214491','DH18DTA',80,0)
insert into Course_Offering Values(N'56',N'214465','DH18DTA',80,0)
insert into Course_Offering Values(N'57',N'214292','DH18DTA',80,0)
insert into Course_Offering Values(N'58',N'214286','DH18DTA',80,0)
insert into Course_Offering Values(N'59',N'214285','DH18DTA',80,0)
insert into Course_Offering Values(N'60',N'214291','DH18DTA',80,0)
insert into Course_Offering Values(N'61',N'214490','DH18DTA',80,0)
insert into Course_Offering Values(N'62',N'214985','DH18DTA',80,0)
insert into Course_Offering Values(N'63',N'214984','DH18DTA',80,0)
insert into Course_Offering Values(N'64',N'214374','DH18DTA',80,0)
-- insert into Schedule
insert into Schedule values(N'1',N'0',null,'LT',3,'20/10/2021','20/11/2021',N'HD',10,13)
insert into Schedule values(N'2',N'1',null,'LT',5,'20/10/2021','20/11/2021',N'PV',10,13)
insert into Schedule values(N'3',N'2',null,'LT',4,'20/10/2021','20/11/2021',N'RD',10,13)
insert into Schedule values(N'4',N'3',null,'LT',2,'20/10/2021','20/11/2021',N'CT',1,4)
insert into Schedule values(N'5',N'4',null,'LT',3,'20/10/2021','20/11/2021',N'RD',7,10)
insert into Schedule values(N'6',N'5',null,'LT',3,'20/10/2021','20/11/2021',N'HD',4,7)
insert into Schedule values(N'7',N'6',null,'LT',5,'20/10/2021','20/11/2021',N'CT',10,13)
insert into Schedule values(N'8',N'6',null,'TH',5,'20/10/2021','20/11/2021',N'RD',10,13)
insert into Schedule values(N'9',N'7',null,'LT',4,'20/10/2021','20/11/2021',N'HD',7,10)
insert into Schedule values(N'10',N'7',null,'TH',2,'20/10/2021','20/11/2021',N'RD',10,13)
insert into Schedule values(N'11',N'8',null,'LT',3,'20/10/2021','20/11/2021',N'HD',1,4)
insert into Schedule values(N'12',N'9',null,'LT',3,'20/10/2021','20/11/2021',N'CT',10,13)
insert into Schedule values(N'13',N'10',null,'LT',2,'20/10/2021','20/11/2021',N'PV',4,7)
insert into Schedule values(N'14',N'11',null,'LT',4,'20/10/2021','20/11/2021',N'CT',7,10)
insert into Schedule values(N'15',N'12',null,'LT',3,'20/10/2021','20/11/2021',N'RD',1,4)
insert into Schedule values(N'16',N'13',null,'LT',3,'20/10/2021','20/11/2021',N'HD',4,7)
insert into Schedule values(N'17',N'14',null,'LT',2,'20/10/2021','20/11/2021',N'CT',1,4)
insert into Schedule values(N'18',N'14',null,'TH',4,'20/10/2021','20/11/2021',N'RD',10,13)
insert into Schedule values(N'19',N'15',null,'LT',4,'20/10/2021','20/11/2021',N'PV',7,10)
insert into Schedule values(N'20',N'16',null,'LT',5,'20/10/2021','20/11/2021',N'PV',10,13)
insert into Schedule values(N'21',N'17',null,'LT',4,'20/10/2021','20/11/2021',N'RD',10,13)
insert into Schedule values(N'22',N'18',null,'LT',2,'20/10/2021','20/11/2021',N'HD',7,10)
insert into Schedule values(N'23',N'19',null,'LT',4,'20/10/2021','20/11/2021',N'RD',7,10)
insert into Schedule values(N'24',N'20',null,'LT',3,'20/10/2021','20/11/2021',N'CT',7,10)
insert into Schedule values(N'25',N'20',null,'TH',3,'20/10/2021','20/11/2021',N'PV',1,4)
insert into Schedule values(N'26',N'21',null,'LT',5,'20/10/2021','20/11/2021',N'PV',1,4)
insert into Schedule values(N'27',N'22',null,'LT',2,'20/10/2021','20/11/2021',N'RD',7,10)
insert into Schedule values(N'28',N'23',null,'LT',3,'20/10/2021','20/11/2021',N'HD',7,10)
insert into Schedule values(N'29',N'24',null,'LT',4,'20/10/2021','20/11/2021',N'PV',10,13)
insert into Schedule values(N'30',N'25',null,'LT',4,'20/10/2021','20/11/2021',N'HD',10,13)
insert into Schedule values(N'31',N'26',null,'LT',5,'20/10/2021','20/11/2021',N'PV',1,4)
insert into Schedule values(N'32',N'26',null,'TH',2,'20/10/2021','20/11/2021',N'RD',4,7)
insert into Schedule values(N'33',N'27',null,'LT',5,'20/10/2021','20/11/2021',N'RD',7,10)
insert into Schedule values(N'34',N'27',null,'TH',2,'20/10/2021','20/11/2021',N'RD',4,7)
insert into Schedule values(N'35',N'28',null,'LT',3,'20/10/2021','20/11/2021',N'PV',7,10)
insert into Schedule values(N'36',N'28',null,'TH',4,'20/10/2021','20/11/2021',N'PV',7,10)
insert into Schedule values(N'37',N'29',null,'LT',2,'20/10/2021','20/11/2021',N'PV',4,7)
insert into Schedule values(N'38',N'30',null,'LT',3,'20/10/2021','20/11/2021',N'HD',7,10)
insert into Schedule values(N'39',N'30',null,'TH',4,'20/10/2021','20/11/2021',N'PV',7,10)
insert into Schedule values(N'40',N'31',null,'LT',2,'20/10/2021','20/11/2021',N'PV',10,13)
insert into Schedule values(N'41',N'31',null,'TH',2,'20/10/2021','20/11/2021',N'PV',4,7)
insert into Schedule values(N'42',N'32',null,'LT',5,'20/10/2021','20/11/2021',N'RD',7,10)
insert into Schedule values(N'43',N'33',null,'LT',3,'20/10/2021','20/11/2021',N'RD',1,4)
insert into Schedule values(N'44',N'33',null,'TH',4,'20/10/2021','20/11/2021',N'PV',1,4)
insert into Schedule values(N'45',N'34',null,'LT',3,'20/10/2021','20/11/2021',N'PV',4,7)
insert into Schedule values(N'46',N'35',null,'LT',3,'20/10/2021','20/11/2021',N'PV',7,10)
insert into Schedule values(N'47',N'35',null,'TH',5,'20/10/2021','20/11/2021',N'RD',7,10)
insert into Schedule values(N'48',N'36',null,'LT',4,'20/10/2021','20/11/2021',N'HD',7,10)
insert into Schedule values(N'49',N'37',null,'LT',4,'20/10/2021','20/11/2021',N'PV',1,4)
insert into Schedule values(N'50',N'37',null,'TH',4,'20/10/2021','20/11/2021',N'PV',4,7)
insert into Schedule values(N'51',N'38',null,'LT',5,'20/10/2021','20/11/2021',N'CT',4,7)
insert into Schedule values(N'52',N'38',null,'TH',3,'20/10/2021','20/11/2021',N'HD',4,7)
insert into Schedule values(N'53',N'39',null,'LT',3,'20/10/2021','20/11/2021',N'RD',4,7)
insert into Schedule values(N'54',N'39',null,'TH',5,'20/10/2021','20/11/2021',N'CT',1,4)
insert into Schedule values(N'55',N'40',null,'LT',2,'20/10/2021','20/11/2021',N'HD',4,7)
insert into Schedule values(N'56',N'41',null,'LT',4,'20/10/2021','20/11/2021',N'CT',1,4)
insert into Schedule values(N'57',N'41',null,'TH',5,'20/10/2021','20/11/2021',N'CT',4,7)
insert into Schedule values(N'58',N'42',null,'LT',4,'20/10/2021','20/11/2021',N'CT',7,10)
insert into Schedule values(N'59',N'42',null,'TH',2,'20/10/2021','20/11/2021',N'CT',1,4)
insert into Schedule values(N'60',N'43',null,'LT',4,'20/10/2021','20/11/2021',N'RD',10,13)
insert into Schedule values(N'61',N'44',null,'LT',4,'20/10/2021','20/11/2021',N'RD',7,10)
insert into Schedule values(N'62',N'44',null,'TH',3,'20/10/2021','20/11/2021',N'CT',4,7)
insert into Schedule values(N'63',N'45',null,'LT',2,'20/10/2021','20/11/2021',N'CT',7,10)
insert into Schedule values(N'64',N'45',null,'TH',4,'20/10/2021','20/11/2021',N'RD',1,4)
insert into Schedule values(N'65',N'46',null,'LT',3,'20/10/2021','20/11/2021',N'HD',7,10)
insert into Schedule values(N'66',N'47',null,'LT',4,'20/10/2021','20/11/2021',N'HD',7,10)
insert into Schedule values(N'67',N'47',null,'TH',4,'20/10/2021','20/11/2021',N'HD',10,13)
insert into Schedule values(N'68',N'48',null,'LT',2,'20/10/2021','20/11/2021',N'CT',7,10)
insert into Schedule values(N'69',N'49',null,'LT',2,'20/10/2021','20/11/2021',N'PV',1,4)
insert into Schedule values(N'70',N'50',null,'LT',4,'20/10/2021','20/11/2021',N'HD',7,10)
insert into Schedule values(N'71',N'50',null,'TH',3,'20/10/2021','20/11/2021',N'CT',10,13)
insert into Schedule values(N'72',N'51',null,'LT',2,'20/10/2021','20/11/2021',N'CT',1,4)
insert into Schedule values(N'73',N'52',null,'LT',5,'20/10/2021','20/11/2021',N'CT',4,7)
insert into Schedule values(N'74',N'52',null,'TH',2,'20/10/2021','20/11/2021',N'CT',1,4)
insert into Schedule values(N'75',N'53',null,'LT',3,'20/10/2021','20/11/2021',N'HD',7,10)
insert into Schedule values(N'76',N'54',null,'LT',3,'20/10/2021','20/11/2021',N'PV',7,10)
insert into Schedule values(N'77',N'55',null,'LT',5,'20/10/2021','20/11/2021',N'RD',7,10)
insert into Schedule values(N'78',N'56',null,'LT',3,'20/10/2021','20/11/2021',N'PV',10,13)
insert into Schedule values(N'79',N'57',null,'LT',4,'20/10/2021','20/11/2021',N'HD',7,10)
insert into Schedule values(N'80',N'58',null,'LT',3,'20/10/2021','20/11/2021',N'CT',7,10)
insert into Schedule values(N'81',N'58',null,'TH',5,'20/10/2021','20/11/2021',N'RD',10,13)
insert into Schedule values(N'82',N'59',null,'LT',5,'20/10/2021','20/11/2021',N'HD',1,4)
insert into Schedule values(N'83',N'59',null,'TH',5,'20/10/2021','20/11/2021',N'PV',1,4)
insert into Schedule values(N'84',N'60',null,'LT',3,'20/10/2021','20/11/2021',N'CT',1,4)
insert into Schedule values(N'85',N'60',null,'TH',3,'20/10/2021','20/11/2021',N'CT',1,4)
insert into Schedule values(N'86',N'61',null,'LT',4,'20/10/2021','20/11/2021',N'CT',7,10)
insert into Schedule values(N'87',N'61',null,'TH',2,'20/10/2021','20/11/2021',N'CT',10,13)
insert into Schedule values(N'88',N'62',null,'LT',5,'20/10/2021','20/11/2021',N'RD',7,10)
insert into Schedule values(N'89',N'63',null,'LT',3,'20/10/2021','20/11/2021',N'RD',1,4)
insert into Schedule values(N'90',N'64',null,'LT',3,'20/10/2021','20/11/2021',N'RD',10,13)
insert into Schedule values(N'91',N'64',null,'TH',3,'20/10/2021','20/11/2021',N'CT',1,4)
-- insert into Student_Schedule

-- insert into Student_ScheduleR

-- insert into Professor_Schedule


-- insert into front_Sub
insert into front_Sub values(N'214331',N'214351')
insert into front_Sub values(N'214242',N'214271')
insert into front_Sub values(N'214252',N'214273')
insert into front_Sub values(N'214441',N'214286')
insert into front_Sub values(N'214370',N'214387')
insert into front_Sub values(N'214331',N'214463')
insert into front_Sub values(N'214241',N'214462')
insert into front_Sub values(N'214352',N'214461')
insert into front_Sub values(N'214241',N'214282')
insert into front_Sub values(N'214331',N'214352')
insert into front_Sub values(N'214462',N'214464')
insert into front_Sub values(N'214241',N'214252')
insert into front_Sub values(N'214442',N'214471')
insert into front_Sub values(N'214241',N'214292')
insert into front_Sub values(N'214442',N'214485')
insert into front_Sub values(N'214463',N'214492')
insert into front_Sub values(N'214462',N'214483')
insert into front_Sub values(N'214441',N'214353')
insert into front_Sub values(N'214370',N'214383')
insert into front_Sub values(N'214462',N'214374')
insert into front_Sub values(N'214462',N'214289')
insert into front_Sub values(N'214331',N'214386')
insert into front_Sub values(N'214442',N'214491')
insert into front_Sub values(N'214331',N'214441')
insert into front_Sub values(N'214442',N'214451')
insert into front_Sub values(N'214241',N'214285')
insert into front_Sub values(N'214442',N'214372')
insert into front_Sub values(N'214331',N'214388')
insert into front_Sub values(N'214331',N'214361')
insert into front_Sub values(N'214370',N'214379')
insert into front_Sub values(N'214252',N'214290')
insert into front_Sub values(N'214321',N'214331')
insert into front_Sub values(N'214463',N'214291')
insert into front_Sub values(N'214242',N'214251')
insert into front_Sub values(N'202110',N'202121')
insert into front_Sub values(N'214442',N'214465')
insert into front_Sub values(N'214463',N'214490')
insert into front_Sub values(N'214352',N'214370')
insert into front_Sub values(N'214252',N'214274')



-- insert into date_exam


insert into Schedule values(N'-1',N'52',null,'TH',2,'20/10/2021','10/12/2021',N'Máy 1',4,10)
insert into Schedule values(N'-2',N'52',null,'LT',2,'20/10/2021','10/12/2021',N'Rạng Đông',3,10)

insert into Date_Exam values (N'2021_2',N'-1','nhom1',40);
insert into Date_Exam values (N'2021_2',N'-2','nhom1',40);

-- insert into Course_Progress
insert into Course_Progress Values('DT',N'202501',18,1);
insert into Course_Progress Values('DT',N'214201',18,1);
insert into Course_Progress Values('DT',N'202109',18,1);
insert into Course_Progress Values('DT',N'202108',18,1);
insert into Course_Progress Values('DT',N'200101',18,1);
insert into Course_Progress Values('DT',N'200102',18,1);
insert into Course_Progress Values('DT',N'214321',18,1);
insert into Course_Progress Values('DT',N'213603',18,1);
insert into Course_Progress Values('DT',N'202206',18,1);
insert into Course_Progress Values('DT',N'200202',18,1);
insert into Course_Progress Values('DT',N'202502',18,1);
insert into Course_Progress Values('DT',N'200103',18,1);
insert into Course_Progress Values('DT',N'213604',18,1);
insert into Course_Progress Values('DT',N'200201',18,1);
insert into Course_Progress Values('DT',N'214331',18,1);
insert into Course_Progress Values('DT',N'202110',18,1);
insert into Course_Progress Values('DT',N'214242',18,1);
insert into Course_Progress Values('DT',N'214231',18,1);
insert into Course_Progress Values('DT',N'202622',18,1);
insert into Course_Progress Values('DT',N'208453',18,0);
insert into Course_Progress Values('DT',N'214441',18,1);
insert into Course_Progress Values('DT',N'214241',18,1);
insert into Course_Progress Values('DT',N'202620',18,0);
insert into Course_Progress Values('DT',N'214251',18,1);
insert into Course_Progress Values('DT',N'202121',18,1);
insert into Course_Progress Values('DT',N'200105',18,1);
insert into Course_Progress Values('DT',N'214352',18,1);
insert into Course_Progress Values('DT',N'214442',18,1);
insert into Course_Progress Values('DT',N'214351',18,1);
insert into Course_Progress Values('DT',N'214361',18,1);
insert into Course_Progress Values('DT',N'214463',18,1);
insert into Course_Progress Values('DT',N'214252',18,1);
insert into Course_Progress Values('DT',N'200107',18,1);
insert into Course_Progress Values('DT',N'214462',18,1);
insert into Course_Progress Values('DT',N'214353',18,0);
insert into Course_Progress Values('DT',N'214372',18,0);
insert into Course_Progress Values('DT',N'214451',18,0);
insert into Course_Progress Values('DT',N'214386',18,0);
insert into Course_Progress Values('DT',N'214282',18,0);
insert into Course_Progress Values('DT',N'214492',18,0);
insert into Course_Progress Values('DT',N'214471',18,0);
insert into Course_Progress Values('DT',N'214461',18,1);
insert into Course_Progress Values('DT',N'214370',18,1);
insert into Course_Progress Values('DT',N'214274',18,0);
insert into Course_Progress Values('DT',N'214388',18,0);
insert into Course_Progress Values('DT',N'214273',18,0);
insert into Course_Progress Values('DT',N'214387',18,0);
insert into Course_Progress Values('DT',N'214485',18,0);
insert into Course_Progress Values('DT',N'214483',18,0);
insert into Course_Progress Values('DT',N'214383',18,0);
insert into Course_Progress Values('DT',N'214289',18,0);
insert into Course_Progress Values('DT',N'214290',18,0);
insert into Course_Progress Values('DT',N'214379',18,0);
insert into Course_Progress Values('DT',N'214271',18,0);
insert into Course_Progress Values('DT',N'214464',18,0);
insert into Course_Progress Values('DT',N'214491',18,0);
insert into Course_Progress Values('DT',N'214465',18,0);
insert into Course_Progress Values('DT',N'214292',18,0);
insert into Course_Progress Values('DT',N'214286',18,1);
insert into Course_Progress Values('DT',N'214285',18,0);
insert into Course_Progress Values('DT',N'214291',18,1);
insert into Course_Progress Values('DT',N'214490',18,1);
insert into Course_Progress Values('DT',N'214985',18,1);
insert into Course_Progress Values('DT',N'214984',18,1);
insert into Course_Progress Values('DT',N'214374',18,1);

-- insert into Sub_Pass
 insert into Sub_Pass values('2018_1', N'202501', N'18130005', 9.57, 3.83, N'A');
 insert into Sub_Pass values('2018_1', N'214201', N'18130005', 9.54, 3.82, N'A');
 insert into Sub_Pass values('2018_1', N'202109', N'18130005', 7.96, 3.18, N'B');
 insert into Sub_Pass values('2018_1', N'202108', N'18130005', 6.67, 2.67, N'B');
 insert into Sub_Pass values('2018_1', N'200101', N'18130005', 7.36, 2.94, N'B');
 insert into Sub_Pass values('2018_1', N'200102', N'18130005', 4.8, 1.92, N'D');
 insert into Sub_Pass values('2018_1', N'214321', N'18130005', 3.1, 1.24, N'F');
 insert into Sub_Pass values('2018_1', N'213603', N'18130005', 8.61, 3.44, N'A');
 insert into Sub_Pass values('2018_1', N'202206', N'18130005', 3.35, 1.34, N'F');
 insert into Sub_Pass values('2018_2', N'200202', N'18130005', 3.28, 1.31, N'F');
 insert into Sub_Pass values('2018_2', N'202502', N'18130005', 6.06, 2.42, N'C');
 insert into Sub_Pass values('2018_2', N'200103', N'18130005', 7.75, 3.1, N'B');
 insert into Sub_Pass values('2018_2', N'213604', N'18130005', 9.33, 3.73, N'A');
 insert into Sub_Pass values('2018_2', N'200201', N'18130005', 4.77, 1.91, N'D');
 insert into Sub_Pass values('2018_2', N'214331', N'18130005', 7.64, 3.06, N'B');
 insert into Sub_Pass values('2018_2', N'202110', N'18130005', 6.62, 2.65, N'B');
 insert into Sub_Pass values('2018_2', N'214242', N'18130005', 5.82, 2.33, N'C');
 insert into Sub_Pass values('2018_2', N'214231', N'18130005', 6.65, 2.66, N'B');

 insert into Sub_Pass values('2019_1', N'202622', N'18130005', 5.49, 2.2, N'C');
 insert into Sub_Pass values('2019_1', N'208453', N'18130005', 5.12, 2.05, N'C');
 insert into Sub_Pass values('2019_1', N'214441', N'18130005', 4.43, 1.77, N'D');
 insert into Sub_Pass values('2019_1', N'214241', N'18130005', 7.51, 3, N'B');
 insert into Sub_Pass values('2019_1', N'202620', N'18130005', 6.3, 2.52, N'C');
 insert into Sub_Pass values('2019_1', N'214251', N'18130005', 9.47, 3.79, N'A');
 insert into Sub_Pass values('2019_1', N'202121', N'18130005', 6.55, 2.62, N'B');

 insert into Sub_Pass values('2019_2', N'200105', N'18130005', 7.48, 2.99, N'B');
 insert into Sub_Pass values('2019_2', N'214352', N'18130005', 4.4, 1.76, N'D');
 insert into Sub_Pass values('2019_2', N'214442', N'18130005', 8.01, 3.2, N'A');
 insert into Sub_Pass values('2019_2', N'214351', N'18130005', 8.11, 3.24, N'A');
 insert into Sub_Pass values('2019_2', N'214361', N'18130005', 6.32, 2.53, N'C');

 insert into Sub_Pass values('2020_1', N'214463', N'18130005', 6.86, 2.74, N'B');
 insert into Sub_Pass values('2020_1', N'214252', N'18130005', 4.12, 1.65, N'D');
 insert into Sub_Pass values('2020_1', N'200107', N'18130005', 5.95, 2.38, N'C');
 insert into Sub_Pass values('2020_1', N'214462', N'18130005', 4.22, 1.69, N'D');
 insert into Sub_Pass values('2020_1', N'214353', N'18130005', 6.62, 2.65, N'B');
 insert into Sub_Pass values('2020_1', N'214372', N'18130005', 6.03, 2.41, N'C');
 insert into Sub_Pass values('2020_1', N'214451', N'18130005', 9.76, 3.9, N'A');
 insert into Sub_Pass values('2020_1', N'214386', N'18130005', 5.57, 2.23, N'C');

 insert into Sub_Pass values('2020_2', N'214282', N'18130005', 3.73, 1.49, N'F');
 insert into Sub_Pass values('2020_2', N'214492', N'18130005', 7.01, 2.8, N'B');
 insert into Sub_Pass values('2020_2', N'214471', N'18130005', 3.6, 1.44, N'F');
 insert into Sub_Pass values('2020_2', N'214461', N'18130005', 5.01, 2, N'C');
 insert into Sub_Pass values('2020_2', N'214370', N'18130005', 9.71, 3.88, N'A');
 insert into Sub_Pass values('2020_2', N'214274', N'18130005', 9.49, 3.8, N'A');
 insert into Sub_Pass values('2020_2', N'214388', N'18130005', 3.63, 1.45, N'F');
 insert into Sub_Pass values('2020_2', N'214273', N'18130005', 3.97, 1.59, N'F');

 insert into Sub_Pass values('2021_1', N'214387', N'18130005', 3.37, 1.35, N'F');
 insert into Sub_Pass values('2021_1', N'214485', N'18130005', 5.21, 2.08, N'C');
 insert into Sub_Pass values('2021_1', N'214483', N'18130005', 3.18, 1.27, N'F');
 insert into Sub_Pass values('2021_1', N'214383', N'18130005', 9.33, 3.73, N'A');
 insert into Sub_Pass values('2021_1', N'214289', N'18130005', 7.78, 3.11, N'B');
 insert into Sub_Pass values('2021_1', N'214290', N'18130005', 5.06, 2.02, N'C');
 insert into Sub_Pass values('2021_1', N'214379', N'18130005', 9.07, 3.63, N'A');
 insert into Sub_Pass values('2021_1', N'214271', N'18130005', 9.59, 3.84, N'A');
 insert into Sub_Pass values('2021_1', N'214464', N'18130005', 8.36, 3.34, N'A');
 insert into Sub_Pass values('2021_1', N'214491', N'18130005', 5.8, 2.32, N'C');
 insert into Sub_Pass values('2021_1', N'214465', N'18130005', 4.12, 1.65, N'D');
 insert into Sub_Pass values('2021_1', N'214292', N'18130005', 5.27, 2.11, N'C');
 
 insert into Sub_Pass values('2021_2', N'214286', N'18130005', 6.96, 2.78, N'B');
 insert into Sub_Pass values('2021_2', N'214285', N'18130005', 5.92, 2.37, N'C');
 insert into Sub_Pass values('2021_2', N'214291', N'18130005', 9.18, 3.67, N'A');
 insert into Sub_Pass values('2021_2', N'214490', N'18130005', 9.71, 3.88, N'A');
 insert into Sub_Pass values('2021_2', N'214985', N'18130005', 4.34, 1.74, N'D');
 insert into Sub_Pass values('2021_2', N'214984', N'18130005', 4.92, 1.97, N'D');
 insert into Sub_Pass values('2021_2', N'214374', N'18130005', 9.58, 3.83, N'A');

 insert into Semester_Result values('2018_1`', N'18130005', 5.93, 2.37, 19);
 insert into Semester_Result values('2018_2', N'18130005', 6.04, 2.42, 21);
 insert into Semester_Result values('2019_1', N'18130005', 6.43, 2.57, 19);
 insert into Semester_Result values('2019_2', N'18130005', 6.82, 2.73, 17);
 insert into Semester_Result values('2020_1', N'18130005', 6.01, 2.4, 28);
 insert into Semester_Result values('2020_2', N'18130005', 3.85, 1.54, 15);
 insert into Semester_Result values('2021_1', N'18130005', 5.92, 2.37, 33);
 insert into Semester_Result values('2021_2', N'18130005', 6.83, 2.73, 32);

 insert into Final_Result values( N'18130005', 5.91, 2.36);