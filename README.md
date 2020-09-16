### - 기간 : 2020.05~2020.09 (4개월)
### - 인원 : 개인 프로젝트
### - Github : <https://github.com/pjh37/pjh-springboot-webservice>

<br/>

사용 기술
====
<hr>

- ### Spring Framework(Gradle)
    + ### Spring Security
    + ### jpa
    + ### lombok
- ### RabbitMQ
- ### Docker
- ### MySQL
- ### Redis
- ### jenkins
- ### Junit5(테스트를 위해 사용)
- ### thymeleaf
<br/>

프로젝트 소개 및 기능
====
<br/>

## 프로젝트 소개
### 공개또는 비공개 그룹을 만들어 게시물과 동영상을 공유하고 스트리밍 서비스를 제공할 수 있습니다.
### 채팅방을 개설하고 원하는 친구에게 코드를 보내 초대하여 함께 채팅할 수 있습니다. 

<br/>

## 기능
------

+ ### 로그인 
    + ### 이메일을 통해 인증해야 guest에서 user로 권한이 변경되어 웹의 기능을 사용할 수 있습니다.
    + ### spring security를 통해 각 페이지에 대한 권한을 관리합니다.

+ ### 그룹 관리자의 권한 관리, 동영상 스트리밍
    + ### 그룹을 만든 사람이 그룹 회원의 권한을 조정할 수 있습니다.
    + ### 그룹 권한에 따라 동영상보기 또는 게시글 작성 등 일부 기능이 제한 됩니다.
    + ### 그룹의 인원, 썸네일 설정가능

+ ### 게시판
    + ### 자신이 쓴 글만 수정, 삭제를 할 수 있습니다.
    + ### 계층형 댓글, 답글을 쓸 수 있습니다.
    + ### 페이징을 적용하여 전체 게시물의 일부만 불러옵니다.

+ ### 채팅
    + ### 채팅방을 개설하고 해당 url등을 보내 원하는 사람들 끼리 채팅을 할 수 있습니다.
    + ### RabbitMQ를 통해 현재 메세지의 전송량등을 대시보드를 통해 볼 수 있습니다.

<br/>

## 어려웠거나 문제였던 점과 해결 방안
-----
+ ### JPA(ORM)적용
    + ### 기존 SQL Mapper문제점
        + ### SQL에 의존적인 개발을 하게 되며 객체를 테이블에 맞춰 모델링 하게된다.
        + ### 반복적이고 중복된 코드와 객체답게 모델링 할수록 매핑 작업만 무수히 증가
    + ### 해결방안 ORM(객체 관계매핑)으로 해결
        + ### SQL중심적인 개발에서 객체 중심으로 개발할 수 있게 되었다.
        + ### 기존에 필드가 변경되면 crud모두를 변경해야 했다면 JPA적용으로 그럴 필요가 없어 유지보수 용이
        + ### Lazy Loading,트랜잭션지원 쓰기지연 기능, fetch등으로 성능을 높힐 수 있었습니다.

<br/>

+ ### 동영상 스트리밍 (AWS s3,cloud front(CDN) 활용)
    + ### 기존코드의 문제점
        <ul>
            <li>
                <h3>
                RandomAccessFile과 request Header의 요청된 range를 읽고 status code를 206을 준다.
                이렇게 할 경우 요청된 부분을 읽어 response.write를 하게 되면 임의 지점을 요청해도 재생이 잘되었으나
                접속자 수를 조금만 증가 시켜도 부하가 폭증하는 것을 발견
                </h3>
            </li>
        </ul>
        
    + ### 해결방안
       <ul>
            <li>
                <h3>
                   AWS의 s3와 cloud front(CDN)을 통해 캐싱 하여 본 서버의 부하를 줄이고 동영상 스트리밍이 더 부드럽게 작동 되도록 하였습니다.
                </h3>
            </li>
       </ul>

<br/>
        
+ ### RabbitMQ 적용
    <ul>
        <li>
            <h3>
                RabbitMQ를 윈도우에 설치할때 여러가지 오류가 많이 발생하게 된다. nbp에서 서버를 빌린뒤
                rabbitmq를 docker이미지로 설치 및 작동시켜 주었습니다.
            </h3>
        </li>
    </ul>
    
    > rabbitmq라는 메세지 큐를 적용한 이유는 rabbitmq가 가진 다른 응용프로그램과 데이터 송수신 기능과
    대용량 데이터를 처리하기 위한 작업분산 기능을 넣을 수 있기 때문입니다.

<br/>

+ ### 편리한 배포를와 리스크 분산을 위한 Docker 적용
    + ### AWS-> Naver Cloud로 이전 작업중 환경 설정의 번거러움, 버전 관리의 어려움등 이전작업에 어려움을 느낌
    + ### 어디를 가도 동일한 환경아래에서 실행 시키고 장애 발생시 쉽고 빠른 재배포를 위해 Docker를 적용했습니다.
    <ul>
        <li>
            <h3>
            spring내장 톰켓안에서 DB,redis를 함께 구동하다가 어느 하나에서 문제가 생기면 다른 곳도 이상이 생기
            는 것을 발견하고 각각을 docker 컨테이너로 분리하여 배포하였습니다.
            </h3>
        </li>
    </ul>
    


    
<br/>

## 기능별 캡처
### 그룹만들기
![그룹만들기1](https://user-images.githubusercontent.com/37110261/88568092-6145c400-d073-11ea-968a-65598c4f8e1f.PNG)


<br/>

### 권한수정
![권한수정](https://user-images.githubusercontent.com/37110261/88568975-be8e4500-d074-11ea-9fbb-a39f6ff401c5.PNG)

<br/>

### 게시글
![게시글](https://user-images.githubusercontent.com/37110261/88568980-c0f09f00-d074-11ea-93ab-a698bd8908b4.PNG)

<br/>

### 동영상업로드
![동영상업로드](https://user-images.githubusercontent.com/37110261/88568989-c3eb8f80-d074-11ea-88c7-8003b8ecf4a8.PNG)

<br/>

### 재생
![재생](https://user-images.githubusercontent.com/37110261/88568999-c6e68000-d074-11ea-8099-8f87579647c9.PNG)

<br/>

### 채팅방
![채팅방](https://user-images.githubusercontent.com/37110261/88569007-c9e17080-d074-11ea-96e4-2aaab4d4edbe.PNG)

<br/>

### 채팅
![채팅1](https://user-images.githubusercontent.com/37110261/88569011-cbab3400-d074-11ea-9963-eb3feb748c42.PNG){:width="800px" height="500px"}

<br/>