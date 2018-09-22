var videoUtils = require('../../utils/videoUtils.js')
const app = getApp()
Page({

  data: {
    cover:'cover',
    videoContext:"",
    videoInfo:{},
    videId:'',
    src:''
  },
  

  showSearch:function(){
    wx.navigateTo({
      url: '../videoSearch/videoSearch',
    })
  },
  onLoad:function(params){
    var me = this;
    me.videoContext = wx.createVideoContext('myVideo', me);
    var videoInfo = JSON.parse(params.videoInfo);
    me.setData({
      videId: videoInfo.id,
      src: app.serverUrl + videoInfo.videoPath,
      videoInfo: videoInfo
    })

  },
  showIndex:function(){
    wx.redirectTo({
      url: '../index/index',
    })
  },

  onShow:function(){
    var me = this;
    me.videoContext.play();
  },
  onHide:function(){
    var me = this;
    me.videoContext.pause();
  },
  upload:function(){
    videoUtils.uploadVideo();
  }
})
