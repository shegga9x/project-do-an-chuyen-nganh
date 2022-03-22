create database Course_Registration
go
use Course_Registration
set dateformat DMY
go
--select * from password_reset_token;
--select * from USER_KIND;
-- User_Kind là bảng nhận dạng(quy ước) về user thôi
-- VD : bảng Student(Sinh Viên) mình quy ước ID_User_Kind là 1
--      bảng Professor(Giảng Viên) mình quy ước ID_User_Kind là 2
-- lúc đó tao quên để bảng student foregin key bảng này
create table USER_KIND
(
	ID_User_Kind nvarchar(2) not null,
	Name_User_Kind nvarchar(50) not null,
	PRIMARY KEY (ID_User_Kind) 
)

--dữ liệu bảng USER_KIND
INSERT INTO USER_KIND VALUES ('st', N'Student')
INSERT INTO USER_KIND VALUES ('pr', N'Professor')
INSERT INTO USER_KIND VALUES ('ad', N'admin')

-- Bảng này cần vcl
create table USERS
    (
	ID_User nvarchar(50)  not null,
	--Admin là ad ,student là st,professor là pr
	ID_User_Kind nvarchar(2) not null FOREIGN KEY REFERENCES USER_KIND(ID_User_Kind),
	email nvarchar(50) not null,
	password nvarchar(255) not null,
	PRIMARY KEY (ID_User)
	)
create table password_reset_token
(
  ID_User nvarchar(50) not null FOREIGN KEY REFERENCES USERS(ID_User),
  token nvarchar(50) not null,
  expiry_Date smalldatetime not null,
  PRIMARY KEY (ID_User)
)
	----
	select * from USERS;
	----
-- một khoa trong trường
create table Faculty
(
    -- mã khoa (VD DH18DTA thì mã khoa là DT)
	ID_Faculty nvarchar(50) not null,
	ID_Faculty_N int not null,
	Name_Faculty nvarchar(50) not null,
	
	Primary key (ID_Faculty)
)
create table Clazz
(
    -- mã khoa (VD DH18DTA thì mã khoa là DT)
	Clazz_code nvarchar(50) not null,
	ID_Faculty nvarchar(50) not null,
	Max_Size tinyint not null,
	Current_Size tinyint not null,
	Primary key (Clazz_code)
)
--select * from Clazz
---
--select * from Faculty where ID_Faculty = 'AV';
---
-- một học sinh của trường
create table Student
(
	ID_Student nvarchar(50)  not null FOREIGN KEY REFERENCES USERS(ID_User) ON DELETE CASCADE,
	Student_Name nvarchar(50) not null,
	-- mã khoa
	ID_Faculty nvarchar(50) not null FOREIGN KEY REFERENCES Faculty(ID_Faculty),
	-- Khóa học của trường (VD DH18DTA thì khóa của trường là 18 vì mình nộp hồ sơ năm 2018)
	-- Cách tính khóa học lấy năm nộp hồ sơ (trong đây là năm tạo tài khoản) trừ cho năm mốc (năm 2000)
	Create_date smalldatetime not null,
	-- Clazz code là mã lớp (VD DH18DTA)
	Clazz_code nvarchar(50) not null FOREIGN KEY REFERENCES Clazz(Clazz_code),
	-- Thêm vào số chứng chỉ bắt buộc ra trường
	-- Ngành CNTT thì tao nhớ ko nhầm tích lũy 145 chỉ là dc ra trường
	Cert_number_required SMALLINT not null, -- Tính năng mới
	-- Số chứng chỉ đã tích lũy được
	Cert_number_accumulated smallint not null, -- Tính năng mới
	Primary key (ID_Student)
)
--
	
--
-- một giáo sư của trường
create table Professor
(
	ID_Professor nvarchar(50)  not null FOREIGN KEY REFERENCES USERS(ID_User) ON DELETE CASCADE,
	Professor_Name nvarchar(50) not null,
	-- mã khoa
	ID_Faculty nvarchar(50) not null FOREIGN KEY REFERENCES Faculty(ID_Faculty),
	-- Khóa học của trường (VD DH18DTA thì khóa của trường là 18 vì mình nộp hồ sơ năm 2018)
	-- Cách tính khóa học lấy năm nộp hồ sơ (trong đây là năm tạo tài khoản) trừ cho năm mốc (năm 2000)
	Create_date date not null,
	-- Degree là cái vẹo gì
	Degree Nvarchar(50),	
	Primary key (ID_Professor)
)
    ----
	--select * from Professor where ID_Professor = '220';
	----
-- Học kỳ
create table Semester
(
	ID_Semester nvarchar(50) not null,
	start_Date date not null,
	end_Date	date not null,
	years smallint,
	number_S smallint not null
	Primary key (ID_Semester)
)
--select * from Semester;
--select ID_Semester from Semester where GETDATE() between start_Date and end_Date
--select * from Semester where YEAR(GETDATE()) = years;
-- bảng tính thời gian đăng ký môn học
create table Time_For_Course_Register
(
    ID_Semester nvarchar(50) not null FOREIGN KEY REFERENCES Semester(ID_Semester),
    start_Date date not null,
    end_Date date not null,
    Primary key(ID_Semester)
)
--
--select * from Semester;
--select * from Time_For_Course_Register tf join Semester se on tf.ID_Semester = se.ID_Semester where GETDATE() between tf.start_Date and tf.end_Date;
--
-- mô tả chi tiết học phần
create table Course
(    
    ID_Course nvarchar(50)  not null,
    ID_Faculty nvarchar(50) not null FOREIGN KEY REFERENCES Faculty(ID_Faculty),
    Name_Course nvarchar(50) not null,
    -- số chứng chỉ học phần (cao nhất là 4 nên để tinyint)	
    Course_certificate tinyint not null,
    -- học viên năm bao nhiêu có thể học
    years int not null,
    -- học kì cố định có môn này sẽ mở nếu/ hk sẽ là 1, 2  / nếu ko thì sẽ là 0
    number_S smallint    
    Primary key (ID_Course)    
)
--
--Select * from Course;
--
-- một lớp được mở cụ thể trong danh sách đăng ký môn học 	
create table Course_Offering
(
	ID_Course_Offering nvarchar(50)  not null,
	ID_Course nvarchar(50) not null FOREIGN KEY REFERENCES Course(ID_Course) ON DELETE CASCADE,
	-- mã lớp (VD DH18DTA)
	Clazz_code nvarchar(50) not null FOREIGN KEY REFERENCES Clazz(Clazz_code),
	-- tinyint max value là 255 (từ 0 đến 255)
	-- max size là số sinh viên tối đa của lớp
	Max_Size tinyint not null,
	-- current size là số sinh viên đang hiện có của lớp
	Current_Size tinyint not null,
	-- lịch học thì sẽ có ngày bắt đầu và ngày kết thúc 
	-- ngày bắt đầu
	Primary key (ID_Course_Offering)
)
-- select * from Course_Offering where Current_Size < 30 and  ID_Course_Offering = '5';
-- select DISTINCT co.ID_Course_Offering from Course_Offering co join Course c on co.ID_Course = c.ID_Course
--                               join Schedule sc on co.ID_Course_Offering = sc.ID_Course_Offering
--                               where sc.Id_Profeesor is null or co.Current_Size < 30
-- select c.Name_Course from Course_Offering co join Course c on co.ID_Course = c.ID_Course where co.ID_Course_Offering = '5'
-- mới sửa lại Schedule
-- môn học có thể là thực hành hành hoặc lý thuyết 
-- thực hành lý thuyết khác ngày khác tiết và có ngày bắt đầu với kết thúc là khác nhau
--
--Select co.* , sc.Start_Day,sc.End_Day,sc.Study_place,sc.Start_Slot,sc.End_Slot,sc.Theoretical,sc.Teaching_Day from Course_Offering co inner join Schedule sc on co.ID_Course_Offering = sc.ID_Course_Offering where co.ID_Course_Offering = '15';
--
create table Schedule
(
	ID_Schedule nvarchar(50) not null,
	ID_Course_Offering nvarchar(50) not null FOREIGN KEY REFERENCES Course_Offering(ID_Course_Offering) ON DELETE CASCADE,	
	-- Mã giáo viên
	-- Sửa lại ID_Professor có thể là null
	Id_Profeesor nvarchar(50) FOREIGN KEY REFERENCES Professor(ID_Professor),
	-- TH là thực hành
	-- LT là lý thuyết
	Theoretical varchar(2) not null,
	-- thứ dạy học
	Teaching_Day smallint not null,
	-- ngày bắt đầu
	Start_Day date not null,
	-- ngày kết thúc
	End_Day date not null,
    -- Địa điểm học
	Study_place nvarchar(50) not null,
	-- tiết bắt đầu
	Start_Slot tinyint not null,
	-- tiết kết thúc
	End_Slot tinyint not null,
	Primary key (ID_Schedule)
)
 -----
-- select * from Schedule where ID_Schedule = '1';
 -----
-- mon hoc tung hoc vien dang ky
create table Student_Schedule
(
    ID_Semester nvarchar(50) not null FOREIGN KEY REFERENCES Semester(ID_Semester),
    ID_Schedule nvarchar(50) not null FOREIGN KEY REFERENCES Schedule(ID_Schedule) ON DELETE CASCADE,
	ID_Student nvarchar(50)  not null FOREIGN KEY REFERENCES Student(ID_Student) ON DELETE CASCADE,
	Primary key (ID_Student,ID_Semester,ID_Schedule)
)
create table Student_Schedule_R
(
    ID_Semester nvarchar(50) not null FOREIGN KEY REFERENCES Semester(ID_Semester),
    ID_Schedule nvarchar(50) not null FOREIGN KEY REFERENCES Schedule(ID_Schedule) ON DELETE CASCADE,
	ID_Student nvarchar(50)  not null FOREIGN KEY REFERENCES Student(ID_Student) ON DELETE CASCADE,
	Primary key (ID_Student,ID_Semester,ID_Schedule)
)
create table Professor_Schedule
(
    ID_Semester nvarchar(50) not null FOREIGN KEY REFERENCES Semester(ID_Semester),
    ID_Schedule nvarchar(50) not null FOREIGN KEY REFERENCES Schedule(ID_Schedule) ON DELETE CASCADE,
	ID_Professor nvarchar(50)  not null FOREIGN KEY REFERENCES Professor(ID_Professor) ON DELETE CASCADE,
	Primary key (ID_Professor,ID_Semester,ID_Schedule)
)
select * from Professor_Schedule;
-- mon hoc trước
create table front_Sub
(
	ID_Course_B nvarchar(50),
	ID_Course nvarchar(50) not null FOREIGN KEY REFERENCES Course(ID_Course),
	Primary key (ID_Course)
)

-- Mon hoc da qua
create table Sub_Pass
-- sub pass ko co id mon hoc la vi id mon hoc trong schedule /thay hoi sai =))
(
    -- Học kỳ
    ID_Semester nvarchar(50) not null FOREIGN KEY REFERENCES Semester(ID_Semester),
    ID_Course nvarchar(50) not null FOREIGN KEY REFERENCES Course(ID_Course) ON DELETE CASCADE,
	ID_Student nvarchar(50) not null FOREIGN KEY REFERENCES Student(ID_Student) ON DELETE CASCADE,
	-- Điểm	
	Score float not null check (Score <= 10 and Score >= 0),
	-- Điểm hệ 4
	Score_System_4 float not null check (Score_System_4 <= 4 and Score_System_4 >= 0),
	-- Đánh giá học lực
	Rated nvarchar(10) not null,
	Primary key (ID_Student,ID_Course,ID_Semester)
)
--update Sub_Pass set Score = 1, Score_System_4 = 1,Rated = 'F' where ID_Student = '18130005' and ID_Course = '214282' and ID_Semester = '2020_2'
--delete from Sub_Pass where ID_Course in ('214282','214370','214461');
--select * from Sub_Pass;
--select * from Student;
--update Student set Cert_number_accumulated = 0 where ID_Student = '18130005';
--select * from Sub_Pass where ID_Student = '18130003' and ID_Course = '214321' and Score > 4.0;
---
--select * from Sub_Pass;
--insert into Sub_Pass values('2020_2','214492','18130006',8,3.2,'A');
-- ket qua theo tung hoc ki
select * from Student_Schedule_R
create table semester_Result
(
	ID_Semester nvarchar(50) not null FOREIGN KEY REFERENCES Semester(ID_Semester),
	--
	ID_Student nvarchar(50) not null FOREIGN KEY REFERENCES Student(ID_Student) ON DELETE CASCADE,
	--diem trung binh trong ki nay 
	grade_Av float,
	--diem trung binh he 4 trong ki nay
	grade_Av_4 float,
	credit_Get smallint,
	Primary key (ID_Semester,ID_Student)
)
--delete from semester_Result;
--select * from semester_Result;
--insert into semester_Result values('2020_2','18130006',9,3.6,17);
--insert into semester_Result values('2020_1','18130006',8,3.2,21);
--insert into Final_Result values(N'18130005',6.6,72)
select COUNT(DISTINCT c.ID_Course) as dem from Professor_Schedule pr join Schedule s on pr.ID_Schedule = s.ID_Schedule
                                                  join Course_Offering co on s.ID_Course_Offering = co.ID_Course_Offering
												  join Course c on c.ID_Course = co.ID_Course
                       where pr.ID_Professor = '224' and pr.ID_Semester = '2021_1'
create table Final_Result
(
ID_Student nvarchar(50) not null FOREIGN KEY REFERENCES Student(ID_Student),
grade_Av float,
grade_Av_4 float,
Primary key (ID_Student)
)
-- tạo bảng bill cho học sinh 
create table Billing_System
(
ID_Semester nvarchar(50) not null FOREIGN KEY REFERENCES Semester(ID_Semester),
ID_Student nvarchar(50) not null FOREIGN KEY REFERENCES Student(ID_Student),
Paymoney float,
creadit smallint,
Primary key (ID_Semester,ID_Student)
)
--select * from Billing_System where ID_Student = '18130005' and ID_Semester = '2020_2';
--delete from Billing_System;
--go 
--select * from Final_Result
--go
-- insert into users
insert into USERS Values(N'18130005','st',N'18130005@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'18130077','st',N'18130077@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'18130001','st',N'18130001@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'18130002','st',N'18130002@st.hcmuaf.edu.vn',N'123456')	
insert into USERS Values(N'18130003','st',N'18130003@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'18130004','st',N'18130004@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'18130006','st',N'18130006@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'19130006','st',N'19130006@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'19130007','st',N'19130007@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'20130006','st',N'20130006@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'20130007','st',N'20130007@st.hcmuaf.edu.vn',N'123456')
-- phòng đào tạo
insert into USERS Values('pdt','ad','pdt','123456')
update USERS set password = '$2a$10$g/AIRfhpFhGPjAnUw5m8qu974.uI71HwrBpjXeYQu4khl8KI.4VgS' where ID_User = '18130006'
select * from USERS
update USERS set ID_User = 'pdt' where ID_User = '1';

--
select * from USERS;
--delete from USERS where ID_User = '18130009';
--
insert into USERS Values(N'224','pr',N'224@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'225','pr',N'225@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'226','pr',N'226@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'227','pr',N'227@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'228','pr',N'228@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'229','pr',N'229@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'220','pr',N'220@st.hcmuaf.edu.vn',N'123456')
insert into USERS Values(N'300','pr',N'300@st.hcmuaf.edu.vn',N'123456')

--
--delete from USERS where ID_User in ('301','302');
--
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

--
select * from Faculty;
--
insert into Clazz Values(N'DH18DTA','DT',100,0)
insert into Clazz Values(N'DH19DTA','DT',100,0)
insert into Clazz Values(N'DH20DTA','DT',100,0)
select * from Clazz where SUBSTRING(Clazz_code,3,2) = '18' and ID_Faculty = 'dt';
--dữ liệu bảng Student
insert into Student Values(N'18130005',N'Đàm Văn Anh','DT','20/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130077',N'Ngô Minh Hiển','DT','22/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130001',N'Nguyễn Văn A','DT','20/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130002',N'Nguyễn Văn B','DT','20/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130003',N'Nguyễn Văn C','DT','20/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130004',N'Nguyễn Văn D','DT','20/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'18130006',N'Nguyễn Văn E','DT','20/10/2018',N'DH18DTA',136,0)
insert into Student Values(N'19130006',N'Nguyễn Văn S','DT','20/10/2019',N'DH19DTA',136,0)
insert into Student Values(N'19130007',N'Nguyễn Văn S','DT','20/10/2019',N'DH19DTA',136,0)
insert into Student Values(N'20130006',N'Nguyễn Văn S','DT','20/10/2020',N'DH20DTA',136,0)
insert into Student Values(N'20130007',N'Nguyễn Văn S','DT','20/10/2020',N'DH20DTA',136,0)

--
select * from USERS
select * from Student;
--delete from USERS where ID_User in ('18130010','18130011','18130012','18130013','18130014','18130015');
--update Student Set Cert_number_accumulated = 4 where ID_Student = '18130006';
select Cert_number_accumulated from Student where ID_Student = '18130006';
--

-- insert into Professor
insert into Professor Values(N'224',N'A','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'225',N'B','DT','20/10/2000',N'Thạc Sĩ')
insert into Professor Values(N'226',N'C','DT','20/10/2000',N'thạc Sĩ')
insert into Professor Values(N'227',N'D','DT','20/10/2000',N'Phó Giáo sư')
insert into Professor Values(N'228',N'E','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'229',N'F','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'220',N'G','DT','20/10/2000',N'Tiến Sĩ')
insert into Professor Values(N'300',N'Van ANh','NH','20/10/2000',N'Tiến Sĩ');
--
select * from Professor;
--delete from Professor where ID_Professor in ('301','302');
select top 1 ID_Professor from Professor order by ID_Professor DESC;
--update Professor set Professor_Name = 'x',ID_Faculty = 'x',Degree='x',Create_date='x' where ID_Professor = 'x'; 
--
-- insert into Semester
insert into Semester Values(N'2018_1','1/9/2018','31/1/2019',2018,1)
insert into Semester Values(N'2018_2','1/3/2019','30/6/2019',2018,2)
insert into Semester Values(N'2019_1','1/9/2019','31/1/2020',2019,1)
insert into Semester Values(N'2019_2','1/3/2020','30/6/2020',2019,2)
insert into Semester Values(N'2020_1','1/9/2020','31/1/2021',2020,1)
insert into Semester Values(N'2020_2','1/3/2021','30/6/2021',2020,2)
insert into Semester Values(N'2021_1','1/9/2021','31/1/2022',2021,1)
insert into Semester Values(N'2021_2','1/3/2022','30/6/2022',2021,2)
update Time_For_Course_Register set end_Date = '25/01/2022' where ID_Semester = '2021_1';
select * from Time_For_Course_Register tf join Semester se on tf.ID_Semester = se.ID_Semester where GETDATE() between tf.start_Date and tf.end_Date
select * from Semester
-- insert into Time_For_Course_Register
insert into Time_For_Course_Register Values(N'2018_1','6/1/2019','12/1/2019')
insert into Time_For_Course_Register Values(N'2018_2','3/5/2019','12/5/2019')
insert into Time_For_Course_Register Values(N'2019_1','6/1/2020','6/1/2020')
insert into Time_For_Course_Register Values(N'2019_2','3/5/2020','12/5/2020')
insert into Time_For_Course_Register Values(N'2020_1','6/1/2021','12/1/2021')
insert into Time_For_Course_Register Values(N'2020_2','3/5/2021','12/5/2021')
insert into Time_For_Course_Register Values(N'2021_1','6/1/2022','27/1/2022')
insert into Time_For_Course_Register Values(N'2021_2','3/5/2022','12/5/2022')
delete from Time_For_Course_Register where ID_Semester in ('2021_1','2021_2'); 
select * from Time_For_Course_Register
select * from Time_For_Course_Register tf where GETDATE() between tf.start_Date and tf.end_Date
select * from Time_For_Course_Register tf join Semester se on tf.ID_Semester = se.ID_Semester where se.ID_Semester = '2021_2' and GETDATE() between tf.start_Date and tf.end_Date
update Time_For_Course_Register set end_Date = '01/02/2022' where ID_Semester = '2021_1';
-- select * from Time_For_Course_Register
-- insert into course
insert into Course Values(N'213603','DT',N'Anh văn 1',4,1,1);
insert into Course Values(N'214201','DT',N'Nhập môn tin học',3,1,1)
insert into Course Values(N'202109','DT',N'Toán cao cấp A2',3,1,1)
insert into Course Values(N'200102','DT',N'Kinh tế chính trị Mác-Lênin',2,1,1)
insert into Course Values(N'214321','DT',N'Lập trình cơ bản',4,1,1)
insert into Course Values(N'202206','DT',N'Vật lý 2',2,1,1)
insert into Course Values(N'202108','DT',N'Toán cao cấp A1',3,1,1)
insert into Course Values(N'202501','DT',N'Giáo dục thể chất 1*',1,1,1)
insert into Course Values(N'200101','DT',N'Triết học Mác Lênin',3,1,1)
insert into Course Values(N'200103','DT',N'Chủ nghĩa xã hội khoa học',2,1,2)
insert into Course Values(N'213604','DT',N'Anh văn 2',3,1,2)
insert into Course Values(N'202110','DT',N'Toán cao cấp A3',3,1,2)
insert into Course Values(N'214331','DT',N'Lập trình nâng cao',4,1,2)
insert into Course Values(N'214231','DT',N'Cấu trúc máy tính',2,1,2)
insert into Course Values(N'214242','DT',N'Nhập môn hệ điều hành',3,1,2)
insert into Course Values(N'208453','DT',N'Marketing căn bản',2,2,1)
insert into Course Values(N'202121','DT',N'Xác suất thống kê',3,2,1)
insert into Course Values(N'214241','DT',N'Mạng máy tính cơ bản',3,2,1)
insert into Course Values(N'214441','DT',N'Cấu trúc dữ liệu',4,2,1)
insert into Course Values(N'202622','DT',N'Pháp luật đại cương',2,2,1)
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
insert into Course Values(N'200107','DT',N'Tư tưởng Hồ Chí Minh',2,3,1)
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
-- 
select Course_certificate from Course where ID_Course = '213603';
select c.Name_Course from Course c where ID_Course = '213603';
-- delete from Course where ID_Course = '213603';
-- delete from front_Sub
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
update Course_Offering set Current_Size = 100 where ID_Course_Offering = '46';
select * from Course_Offering
-- select * from Course_Offering co where co.ID_Course = '214274';
-- delete from Course_Offering where ID_Course_Offering in ('14','18','41','42');
-- insert into Course_Offering Values(N'21',N'202622','DH18DTA',80,100)
-- UPDATE dbo.Course_Offering SET Current_Size = 40 WHERE ID_Course_Offering = '38';
-- insert into Schedule
-- lười chèn vc
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

delete from Schedule where ID_Schedule in ('49','50');
-- 
update Schedule set Id_Profeesor = '224' where ID_Schedule = '8';
update Schedule set Id_Profeesor = null where ID_Schedule = '1';
select ID_Schedule from Schedule where ID_Course_Offering = '38';
select * from Schedule;
-- delete from Schedule where ID_Schedule = '44';
-- insert into Student_Schedule
-- 
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
-- select * from Student_Schedule
-- select TOP 1 st.ID_Semester from Student_Schedule st where st.ID_Student = '18130006' group by st.ID_Semester order by st.ID_Semester desc
-- select TOP 3 st.ID_Semester from Student_Schedule st where st.ID_Student = '18130006' group by st.ID_Semester order by st.ID_Semester desc
-- select st.ID_Semester from Student_Schedule st where st.ID_Semester='2020_2' and st.ID_Student='18130006';
-- delete from Student_Schedule where ID_Semester='2020_2' and ID_Schedule='11' and ID_Student='18130005';
-- delete from Student_Schedule;
--
select * from semester_Result sr join Student st on sr.ID_Student=st.ID_Student where st.ID_Student='18130006' and sr.ID_Semester<='2021_2' order by sr.ID_Semester
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
-- delete from Student_Schedule_R
select * from Student_Schedule_R;
select DISTINCT strr.ID_Student from Student_Schedule_R strr where ID_Semester = '2020_2';
select DISTINCT c.ID_Course from Student_Schedule_R strr join Schedule sc on strr.ID_Schedule = sc.ID_Schedule
                                         join Course_Offering co on sc.ID_Course_Offering = co.ID_Course_Offering
                                         join Course c on co.ID_Course = c.ID_Course
where strr.ID_Student = '18130005' and strr.ID_Semester = '2020_2'
                                        
--
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
select pr.ID_Schedule from Professor_Schedule pr
select * from Professor_Schedule
select COUNT(*) as dem from Professor_Schedule pr where pr.ID_Professor = '224' and pr.ID_Semester = '2020_2';
-- delete from Professor_Schedule where ID_Semester = '2020_2' and ID_Schedule='1' and ID_Professor='224';
-- insert into front_Sub
-- delete from front_Sub;
insert into front_Sub values(N'214331',N'214321')
insert into front_Sub values(N'214441',N'214331')

-- insert into Sub_Pass
select st.ID_Schedule from Student_Schedule st where st.ID_Semester=N'2020_2' and st.ID_Student='18130005'
-- delete from Sub_Pass where ID_Student='18130005' and ID_Course='214282';
select * from Sub_Pass;

--insert into Sub_Pass values('2_2022',N'2142',N'18130006',7.5,N'khá')
insert into Sub_Pass values('2021_1',N'214492',N'18130005',6.5,2.5,N'C')
--insert into Sub_Pass values('3_2022',N'4111',N'18130004',7.5,N'Khá')
--insert into Sub_Pass values('2021_2',N'2142',N'18130005',7.5,N'Khá')
select  * from semester_Result ;
-- delete from semester_Result where ID_Semester='2021_1'
select * from semester_Result sr join Student st on sr.ID_Student=st.ID_Student where st.ID_Student='18130005' and sr.ID_Semester<'2021_1' order by sr.ID_Semester 
-- insert into semester_Result
-- như cc
insert into semester_Result values('2020_2',N'18130005',6.37,2.23,4)
insert into semester_Result values('2021_1',N'18130005',6.0,2.0,4)
insert into semester_Result values('2021_2',N'18130005',7.6,2.5,16)
select se.ID_Semester from semester_Result se where ID_Student='18130005' order by  se.ID_Semester;
--insert into semester_Result values('3_2022',N'18130006',8.6,18)
--insert into semester_Result values('2021_2',N'18130005',2.6,17)
--insert into semester_Result values('3_2021',N'18130002',6.6,12)

-- insert into Final_Result
-- như cc
--insert into Final_Result values(N'18130005',6.6,72)
--insert into Final_Result values(N'18130006',5.6,72)
--insert into Final_Result values(N'18130002',7.6,70)
--insert into Final_Result values(N'18130003',4.6,67)
--insert into Final_Result values(N'18130004',9.6,40)
--insert into Final_Result values(N'18130001',10,30)
	
go
-- thời khóa biểu có học sinh , thời khóa biểu của giáo viên chỉ cần gọi schdule
create FUNCTION Time_Table_St (@ID_User varchar(50))
RETURNS TABLE 
as
RETURN  
select sd.ID_Schedule,sd.ID_Course_Offering from Schedule sd join Course_Offering co on sd.ID_Course_Offering = co.ID_Course_Offering
									 join Student_Schedule stc on stc.ID_Schedule = sd.ID_Schedule 
			where stc.ID_Student = @ID_User and stc.ID_Semester in (select ID_Semester from Semester where GETDATE() between start_Date and end_Date)
go

select sd.* from Time_Table_St('19130006') sd order by sd.ID_Course_Offering;

go
create FUNCTION sub_Passed (@ID_Course_B nvarchar(50),@ID_User nvarchar(50))
RETURNS nvarchar(50) 
as
begin
Declare @ID_Course_B1 nvarchar(50)
(select @ID_Course_B1 = fs.ID_Course_B  from front_Sub fs where fs.ID_Course_B = @ID_Course_B and  fs.ID_Course = case
when (select ID_Course  from Sub_Pass where ID_Course = fs.ID_Course  and ID_Student = @ID_User and Score >= 4) is not null then fs.ID_Course
else null end)
RETURN @ID_Course_B1;
end

go

select  [dbo].sub_Passed (N'214331',N'18130005')
go

-- những môn sẽ hiển thị khi nhấn đăng ký môn học
-- những môn có thể đăng ký của giáo viên thì chọn những môn nào trong bảng schedule có chỗ id pr là null
create FUNCTION Sub_Available_ST (@ID_User varchar(50))
RETURNS TABLE
as
RETURN  
select sc.ID_Schedule from Course_Offering co join Course c on c.ID_Course = co.ID_Course
													 join Schedule sc on sc.ID_Course_Offering = co.ID_Course_Offering
													 
where  
c.ID_Faculty =  case when c.ID_Faculty is null then c.ID_Faculty else (select ID_Faculty from Student where ID_Student = @ID_User) end and
c.years =  case when c.years is null then c.years else (select (YEAR(GETDATE())-YEAR(Create_date)) from Student where ID_Student = @ID_User) end and
c.number_S =  case when c.number_S is null then c.number_S else (select number_S from Semester where ID_Semester in (select ID_Semester from Semester where GETDATE() between start_Date and end_Date)) end and
c.ID_Course = case when (select ID_Course_B from front_Sub where ID_Course_B = c.ID_Course) is null then c.ID_Course 
when [dbo].sub_Passed (c.ID_Course,@ID_User) is null then null else [dbo].sub_Passed (c.ID_Course,@ID_User) end
go
select * from Sub_Available_ST('18130006');
go
select * from Semester
select * from Faculty
select * from Course_Offering
select * from Student
-- bảng này là bảng check khi nhấn vào ô chọn môn học nếu trùng giờ trùng ngày , trùng môn nếu rỗng thì ko đk được

go


create FUNCTION check_Start_Slot_TeachDay (@ID_User varchar(50),@Start_Slot varchar(50),@Teaching_Day varchar(50))
RETURNS TABLE 
as
RETURN  
select sc.Start_Slot from Schedule sc join Student_Schedule stc on sc.ID_Schedule = stc.ID_Schedule
					   where stc.ID_Student = @ID_User 
					   and stc.ID_Semester in  (select ID_Semester from Semester where GETDATE() between start_Date and end_Date)
					   and sc.Start_Slot = @Start_Slot and sc.Teaching_Day = @Teaching_Day					   					   					   
go
create FUNCTION check_Sub_Exist (@ID_User varchar(50))
RETURNS TABLE 
as
RETURN  
select c.ID_Course from Schedule sc join Student_Schedule stc on sc.ID_Schedule = stc.ID_Schedule
						join Course_Offering co on co.ID_Course_Offering = sc.ID_Course_Offering
						join Course c on c.ID_Course = co.ID_Course
						where stc.ID_Student  = @ID_User and stc.ID_Semester in  (select ID_Semester from Semester where GETDATE() between start_Date and end_Date)
go
select * from check_Sub_Exist('18130006');
go
create FUNCTION check_DayST (@ID_Schedule nvarchar(50),@ID_User varchar(50))
RETURNS TABLE 
as
RETURN  
select sc.ID_Schedule from Schedule sc join Course_Offering co on co.ID_Course_Offering = sc.ID_Course_Offering						  
where  (sc.Start_Slot in (select Start_Slot from check_Start_Slot_TeachDay(@ID_User,sc.Start_Slot,sc.Teaching_Day))
 or co.ID_Course in (select ID_Course from check_Sub_Exist(@ID_User))) and (co.Current_Size < co.Max_Size)
 and sc.ID_Schedule = @ID_Schedule
 go
 -- a là y chang nên ko được
 -- b là khác môn nhưng trùng giờ
 -- 20 là được
 --insert into Course_Offering Values(N'37',N'214282','DH18DTA',80,0)
 --insert into Schedule values(N'37',N'36',N'229','LT',3,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
--insert into Schedule values(N'1',N'1',N'224','LT',4,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
--insert into Schedule values(N'1a',N'1',N'224','LT',4,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
--insert into Schedule values(N'1c',N'1',N'224','LT',4,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
--insert into Schedule values(N'1b',N'20',N'224','LT',4,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
--insert into Schedule values(N'20',N'19',N'229','TH',3,'20/10/2021','20/11/2021',N'Rạng Đông',1,4)
--select * from Schedule;

--insert into Student_Schedule values('2020_2',N'1',N'18130005')
--insert into Student_Schedule values('2020_2',N'2',N'18130005')
--insert into Student_Schedule values('2020_2',N'3',N'18130005')
--insert into Student_Schedule values('2020_2',N'4',N'18130005')
--select * from Student_Schedule
--
 select * from check_Start_Slot_TeachDay(N'18130006',2,2);
 select * from check_DayST(N'37','18130006');
 select * from Schedule where ID_Schedule = '41'
-- select * from check_DayST(N'1b','18130005');
-- select * from check_DayST(N'1c','18130005');
-- select * from check_DayST(N'20','18130005');
-- select * from Student_Schedule
 go
 --select * from Student_Schedule st join Schedule sch on st.ID_Schedule = sch.ID_Schedule where st.ID_Semester='2020_2' and st.ID_Student='18130006'
 --select * from Schedule where ID_Schedule = '38'
 --select * from course_offering where ID_course_offering = '37'
-- tạo trigger cho course_offering


go
create FUNCTION check_Sub_Exist_For_Professor (@ID_User varchar(50))
RETURNS TABLE 
as
RETURN  
select c.ID_Course from Schedule sc join Professor_Schedule prc on sc.ID_Schedule = prc.ID_Schedule
						join Course_Offering co on co.ID_Course_Offering = sc.ID_Course_Offering
						join Course c on c.ID_Course = co.ID_Course
						where prc.ID_Professor  = @ID_User and prc.ID_Semester in  (select ID_Semester from Semester where GETDATE() between start_Date and end_Date)
go
select * from check_Sub_Exist_For_Professor('224');
go
-- Thời khóa biểu cho giáo viên
create FUNCTION Time_Table_Pr (@ID_Professor varchar(50))
RETURNS TABLE 
as
RETURN  
select sd.* from Schedule sd join Course_Offering co on sd.ID_Course_Offering = co.ID_Course_Offering
									 join Professor_Schedule prc on prc.ID_Schedule = sd.ID_Schedule 
			where prc.ID_Professor = @ID_Professor and prc.ID_Semester in (select ID_Semester from Semester where GETDATE() between start_Date and end_Date )
go

select * from Time_Table_Pr('224');
go
--Phương thức check những môn mà giáo viên có thể đk ký
create function check_Subject_For_Professor(@ID_Professor nvarchar(50))
returns table 
as
return
select sc.ID_Schedule
from Schedule sc join Course_Offering co on sc.ID_Course_Offering = co.ID_Course_Offering
                 join Course c on co.ID_Course = c.ID_Course
where sc.Id_Profeesor is null and (c.ID_Faculty = (select pf.ID_Faculty from Professor pf where pf.ID_Professor = @ID_Professor) or c.ID_Faculty is null );
go
select * from check_Subject_For_Professor('220');
select * from Student
select * from USERS
go
-- Phương thức check có trùng ngày và giờ ko
create FUNCTION check_Start_Slot_Teach_Day_PR (@ID_Professor varchar(50),@Start_Slot varchar(50),@Teaching_Day varchar(50))
RETURNS TABLE 
as
RETURN  
select sc.Start_Slot from Schedule sc join Professor_Schedule prc on sc.ID_Schedule = prc.ID_Schedule
					   where prc.ID_Professor = @ID_Professor and prc.ID_Semester in  (select ID_Semester from Semester where GETDATE() between start_Date and end_Date)
					   and prc.ID_Semester in  (select ID_Semester from Semester where GETDATE() between start_Date and end_Date)
					   and sc.Start_Slot = @Start_Slot and sc.Teaching_Day = @Teaching_Day					   
go
--
select sp.* ,c.Course_certificate from Sub_Pass sp join Course c on sp.ID_Course = c.ID_Course
go

--Phương thức đk cho giáo viên
create FUNCTION check_Day_Pr (@ID_Schedule nvarchar(50),@ID_Professor varchar(50))
RETURNS TABLE 
as
RETURN  
select sc.ID_Schedule from Schedule sc  join Course_Offering co on co.ID_Course_Offering = sc.ID_Course_Offering						  
where  (sc.Start_Slot in (select Start_Slot from check_Start_Slot_Teach_Day_PR(@ID_Professor,sc.Start_Slot,sc.Teaching_Day))
 or co.ID_Course   in (select ID_Course from check_Sub_Exist_For_Professor(@ID_Professor)))
 and sc.ID_Schedule = @ID_Schedule
 --and (select count(ID_Schedule) from Professor_Schedule where ID_Professor = @ID_Professor) <= 3
 go
 select * from check_Day_Pr('36','224');
 go
-- tạo function semester_Result
select st.* from Student_Schedule st where st.ID_Semester='2020_2' and st.ID_Student='18130005'
go
select * from semester_Result sr join Student st on sr.ID_Student=st.ID_Student where st.ID_Student='18130006' and sr.ID_Semester<='2021_1' order by sr.ID_Semester 
go
create function get_Semester_Reuslt(@ID_Student nvarchar(50),@ID_Semester nvarchar(50))
returns table
as
return 
select  c.ID_Course,c.Name_Course,c.Course_certificate,sp.Score,sp.Score_System_4 from Sub_Pass sp 
																					join Course c on sp.ID_Course = c.ID_Course
																				
where sp.ID_Student = @ID_Student and sp.ID_Semester = @ID_Semester
go
--
select * from get_Semester_Reuslt('18130006','2021_1');
-- tính điểm TB từ semester_Result
select SUM(gr.Score * gr.Course_certificate)/SUM(gr.Course_certificate) Diem_TB from get_Semester_Reuslt('18130005','2_2018') gr;
-- tính điểm TB từ semester_Result(hệ 4)
select SUM(gr.Score_System_4 * gr.Course_certificate)/SUM(gr.Course_certificate) Diem_TB_He_4 from get_Semester_Reuslt('18130005','2_2018') gr;
-- lấy tổng tín chỉ đã đạt trong học kỳ(ko lấy môn dưới 4.0)
select SUM(gr.Course_certificate) so_TC from get_Semester_Reuslt('18130005','2_2018') gr where gr.Score > 4.0;
-- tổng tín chỉ đã đăng ký trong học kỳ
select SUM(gr.Course_certificate) so_TC from get_Semester_Reuslt('18130005','2_2018')gr;
--

go
--
-- tạo function Final_Result(éo biết tính như nào)
--
--
select * from Clazz
select * from Student
delete Student where ID_Student like '18135%'
delete Clazz where Clazz_code like 'DH18TY%'
select * from Course_Offering
UPDATE dbo.Course_Offering SET ID_Course = '213603', Clazz_code = 'DH18DTA', Max_Size = 80,Current_Size = 0 WHERE ID_Course_Offering = '1'
---
go
create Trigger check_Course_Offering
on Course_Offering
for insert,update
as
begin
Declare @CurrentSizeI tinyint = (select I.Current_Size from inserted I);
Declare @MaxSizeI tinyint = (select I.Max_Size from inserted I );
if @CurrentSizeI > @MaxSizeI
begin
RAISERROR(N'lớp đã đầy',11,1)
ROLLBACK TRANSACTION
end
end
go

---
select sa.ID_Schedule from Sub_Available_ST('18130005') sa join Time_Table_St('18130005') tt on sa.ID_Schedule = tt.ID_Schedule where sa.ID_Schedule = '12';
--- Nhập điểm cho giáo viên
select DISTINCT c.ID_Course,c.Name_Course,pr.ID_Semester from Professor_Schedule pr join Schedule sc on pr.ID_Schedule = sc.ID_Schedule join Course_Offering co on sc.ID_Course_Offering = co.ID_Course_Offering join Course c on co.ID_Course = c.ID_Course
where pr.ID_Semester = (select s.ID_Semester from Semester s where GETDATE() between s.start_Date and s.end_Date) and pr.ID_Professor = '224';

select st.ID_Student,s.Student_Name from Student_Schedule st join Schedule sc on st.ID_Schedule = sc.ID_Schedule
                                                             join Course_Offering co on sc.ID_Course_Offering = co.ID_Course_Offering
                                                             join Course c on co.ID_Course = c.ID_Course
                                                             join Student s on st.ID_Student = s.ID_Student
where c.ID_Course = '214282' and st.ID_Semester = '2020_2'; 

insert into Sub_Pass values('2020_2','214282','18130005',8.0,3.6,'A');
select sp.Score from Sub_Pass sp where sp.ID_Student = '18130005' and sp.ID_Course = '214282' and sp.ID_Semester = '2020_2';

------ xét nó có trong cơ sỡ dữ liệu hay chưa
delete from Student_Schedule_R;
select strr.* from Student_Schedule_R strr
where strr.ID_Semester = '2020_2' and strr.ID_Student = '18130005' and strr.ID_Schedule = '36'
insert into Student_Schedule_R values('2020_2','36','18130005')
select st.ID_Schedule from Student_Schedule st
where st.ID_Semester = '2020_2' and st.ID_Student = '18130005'

select Count(DISTINCT c.ID_Course) as dem from Student_Schedule st join Schedule sc on st.ID_Schedule = sc.ID_Schedule
                                               join Course_Offering co on sc.ID_Course_Offering = co.ID_Course_Offering
                                               join Course c on co.ID_Course = c.ID_Course
where st.ID_Semester = '2020_2' and st.ID_Student = '18130005'
                                          


  ---test
go
--create FUNCTION laytinchi (@ID_User varchar(50),@ID_semeseter varchar(50))
--RETURNS TABLE
--as
--RETURN  
--select c.Course_certificate,c.ID_Course,sp.Score from Course_Offering co join Course c on c.ID_Course = co.ID_Course
--													 join Schedule sc on sc.ID_Course_Offering = co.ID_Course_Offering
--													 join Sub_Pass sp on c.ID_Course=sp.ID_Course
													 
--where  
--c.ID_Faculty =  case when c.ID_Faculty is null then  c.ID_Faculty else (select ID_Faculty from Student where ID_Student = @ID_User)  end and
--c.years =  case when c.years is null then  c.years else (select (YEAR(GETDATE())-YEAR(Create_date)) from Student where ID_Student = @ID_User) end and
--c.number_S =  case when c.number_S is null then  c.number_S else (select number_S from Semester where ID_Semester=@ID_semeseter )  end and
--c.ID_Course = case when (select ID_Course_B from front_Sub where ID_Course_B = c.ID_Course) is null then  c.ID_Course
--when [dbo].sub_Passed (c.ID_Course,@ID_User)  is null then null else [dbo].sub_Passed (c.ID_Course,@ID_User)  end

--select * from laytinchi('18130005','2020_2');
