# TRP

트리플 여행자 클럽 마일리지 서비스 설명

# 실행방법
- 개발IDE에 프로젝트를 import 받으신 후 spring boot application을 실행하여 API를 확인하실 수 있습니다.

# 개발환경
- 개발 환경은 Spring boot, jpa를 사용하였습니다.
- 데이터베이스는 h2 database(인메모리DB)를 사용하여 어플리케이션 구동시 같이 구동됩니다.
     로컬환경에서 실행시 http://localhost:8080/h2-console 로 콘솔창에 접속할 수 있습니다.
  * 접속정보 *
  Saved Settings : Generic H2(Embedded)
  Setting Name : Generic H2(Embedded)
  Driver Class : org.h2.Driver
  JDBC URL : jdbc:h2:mem:trpdb
  User Name : trp
  Password : trp
  
- swagger(URL : http://localhost:8080/swagger-ui.html)를 통해 API를 실행해볼 수 있습니다.
  * 테스트 요청 데이터 *
  	리뷰이벤트
    - {type:"REVIEW",action:"ADD",reviewId:"240a0658-dc5f-4878-9381-ebb7b2667773",content:"좋아요!",attachedPhotoIds:["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2- 851d-4a50-bb07-9cc15cbdc332"],userId:"3ede0ef2-92b7-4817-a5f3-0c575361f746",placeId:"2e4baf1c-5acb-4efb-a1af-eddada31b00g"}
	- {type:"REVIEW",action:"MOD",reviewId:"240a0658-dc5f-4878-9381-ebb7b2667772",content:"아주 좋아요!",attachedPhotoIds:[],userId:"3ede0ef2-92b7-4817-a5f3-0c575361f745",placeId:"2e4baf1c-5acb-4efb-a1af-eddada31b00f"}
    - {type:"REVIEW",action:"DELETE",reviewId:"240a0658-dc5f-4878-9381-ebb7b2667772",content:"좋아요!",attachedPhotoIds:["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2- 851d-4a50-bb07-9cc15cbdc332"],userId:"3ede0ef2-92b7-4817-a5f3-0c575361f745",placeId:"2e4baf1c-5acb-4efb-a1af-eddada31b00f"}

  	사용자별 리뷰 보상점수 합
    - {userId: "3ede0ef2-92b7-4817-a5f3-0c575361f745"}
