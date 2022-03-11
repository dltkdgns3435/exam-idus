### 1. 레플리케이션 된 데이터베이스에 연결시 exam 프로필로 실행해야 합니다.
### 2. 레디스 포트 설정 이슈로 패키지 경로로 테스트 실행시 통합테스트의 경우 실패하는 경우가 있습니다.(통합테스트는 클래스로 테스트 해주시면 감사하겠습니다.)
### 3. 스웨커 URl은 {domain}/swagger-ui/index.html#/ 입니다.
### 4. 테이블 생성 쿼리 (ddl create)
* create sequence idx_member start with 1 increment by 50 
* create table member (id bigint not null, email varchar(100) not null, name varchar(20) not null, nickname varchar(30) not null, password varchar(100) not null, phone varchar(20) not null, sex varchar(1), primary key (id))
* create table orders (order_no varchar(12) not null, order_at timestamp, product_name varchar(100) not null, member_id bigint, primary key (order_no))
* alter table member add constraint UK_mbmcqelty0fbrvxp1q58dn57t unique (email)
* create index idx_order_orderno on orders (order_no)
* alter table orders add constraint FKpktxwhj3x9m4gth5ff6bkqgeb foreign key (member_id) references member
