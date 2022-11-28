# FX_sideProject
##-說明
這是一個基於spring boot的買賣空氣單的外匯保證金交易的網頁後端
使用者可以在進入真正的交易市場之前先用這個練練手感
雖然事實上是我寫這個小程式來練對於spring的手感就是了

##-技術說明
###資料庫相關
-使用了mysql資料庫
-使用了springdataJPA提供的方案與後端對接
  具體上是用了hibernate與hikari

###使用者Api
-使用者能在註冊後登入平台
-登入前使用http request與後端對接
-登入後使用者與後端會建立起websocket以利於交易

##感言
本文還未完成整還在修改中



