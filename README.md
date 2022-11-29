# FX_practiceProject

*2022-11-29 21:56:28 Tuesday*



    說明
這是一個基於spring boot的買賣空氣單的外匯保證金交易的網頁後端  
使用者可以在進入真正的交易市場之前先用這個練練手感  



    技術
### 資料庫
- 使用的是mySql資料庫   
- 資料庫與後端對接的方案是springDataJPA  
   具體上是用了spring預設的hibernate與hikari  

### Api相關
- 使用者以restfulApi 簡單註冊與登入  
- 登入後使用者與後端會建立起websocket以利於交易  
	webSocket組件與登入器見Static中的newIndex.html  

### 後端主體
- 利用springBoot搭建了MVC架構  
- 專案管理使用了maven  
- 使用AOP來對外匯資料更新的響應與推送  



