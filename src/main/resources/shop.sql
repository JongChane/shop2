create table useraccount (
   userid varchar(10) primary key,
   password varchar(15),
   username varchar(20),
   phoneno varchar(20),
   postcode varchar(7),
   address varchar(30),
   email varchar(50),
   birthday datetime
);

drop table sale;
CREATE TABLE sale (  --주문정보
	saleid int PRIMARY KEY,  
	userid varchar(10) NOT NULL, 
	saledate datetime,
	foreign key (userid) references useraccount (userid)
);
select * from sale
drop table saleitem;
CREATE TABLE saleitem (   --주문상품
	saleid int ,
	seq int ,
	itemid int NOT NULL,
	quantity int,
	PRIMARY KEY (saleid, seq),
	foreign key (saleid) references sale (saleid),
	foreign key (itemid) references item (id)
);
select * from saleitem

create table board (
   num int primary key,   #게시글번호. 기본키
   writer varchar(30),    #작성자이름
   pass varchar(20),      #비밀번호
   title varchar(100),    #글제목
   content varchar(2000), #글내용
   file1 varchar(200),    # 첨부파일명
   boardid varchar(2),    # 게시판종류:1:공지사항,2:자유게시판,3:QNA
   regdate datetime,      # 등록일시
   readcnt int(10),       # 조회수. 상세보기 시 1씩증가
   grp int,               # 답글 작성시 원글의 게시글번호
   grplevel int(3),       # 답글의 레벨. 
   grpstep int(5)         # 그룹의 출력 순서
);
select * from board