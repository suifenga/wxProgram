var videoUtils = require('../../utils/videoUtils.js')
const app = getApp()
Page({

  data: {
    cover:'cover',
    videoContext:"",
    videoInfo:{},
    videId:'',
    src:'',
    userLikeVideo:false,
    serverUrl:'',
    publisher:[],
    commentsPage: 1,
    commentsTotalPage: 1,
    commentsList: [],
    commentFocus:true,

    placeholder: "说点什么..."
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
    var videoWidth = videoInfo.videoWidth;
    var videoHeight = videoInfo.videoHeight;
    var cover = 'cover';
    if (videoWidth > videoHeight){
      cover = '';
    }
    me.setData({
      videId: videoInfo.id,
      src: app.serverUrl + videoInfo.videoPath,
      videoInfo: videoInfo,
      cover: cover
    })

    var serverUrl = app.serverUrl;
    var user = app.getGlobalUserInfo();
    var loginUserId = "";
    if (user != null && user != undefined && user != '') {
      loginUserId = user.id;
    }
    wx.request({
      url: serverUrl + '/user/queryPublisher?loginUserId=' + loginUserId + "&videoId=" + videoInfo.id + "&publishUserId=" + videoInfo.userId,
      method: 'POST',
      success: function (res) {
        console.log(res.data);

        var publisher = res.data.data.publisher;
        var userLikeVideo = res.data.data.userLikeVideo;
        me.getCommentsList(1);
        me.setData({
          serverUrl: serverUrl,
          publisher: publisher,
          userLikeVideo: userLikeVideo
        });
      }
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
 
    var me = this;
    var userInfo = app.getGlobalUserInfo();

    var videoInfo = JSON.stringify(me.data.videoInfo);
    var realUrl = '../videoInfo/videoInfo#videoInfo@' + videoInfo;

    if (userInfo.id == '' || userInfo.id == undefined) {
      wx.navigateTo({
        url: '../userLogin/userLogin?realUrl=' + realUrl,
      })
    } else {
      videoUtils.uploadVideo();
    }


  },
  showMine: function () {
    var me = this;
    var userInfo = app.getGlobalUserInfo();

    var videoInfo = JSON.parse

    if (userInfo.id == '' || userInfo.id == undefined){
      wx.navigateTo({
        url: '../userLogin/userLogin',
      })
    }else{
      wx.navigateTo({
        url: '../mine/mine',
      })
    }
    
   
  },

  likeVideoOrNot: function () {
    var me = this;
    var userInfo = app.getGlobalUserInfo();


    var videoInfoStr = JSON.stringify(me.data.videoInfo);
    var realUrl = '../videoInfo/videoInfo#videoInfo@' + videoInfoStr;
    if (userInfo.id == '' || userInfo.id == undefined) {
      wx.navigateTo({
        url: '../userLogin/userLogin?realUrl=' + realUrl,
      })
    } else {
      var videoInfo = me.data.videoInfo;
      var userLikeVideo = me.data.userLikeVideo;
      var url = "/video/userLike?userId=" + userInfo.id + "&videoId=" + videoInfo.id + "&videoCreaterId=" + userLikeVideo.userId;

      if (userLikeVideo){
        var url = "/video/userUnLike?userId=" + userInfo.id + "&videoId=" + videoInfo.id + "&videoCreaterId=" + userLikeVideo.userId;
      }
      wx.showLoading({
        title: '....',
      })
      wx.request({
        url: app.serverUrl + url,
        method: "POST",
        header: {
          'content-type': 'application/json', // 默认值
          'headerUserId': userInfo.id,
          'headerUserToken': userInfo.userToken
        },
        success: function (res) {
          wx.hideLoading();
          me.setData({
            userLikeVideo: !userLikeVideo,
          })
        }
      })

    }


  },
  showPublisher:function(){
    var me = this;
    var userInfo = app.getGlobalUserInfo();


    var videoInfo = me.data.videoInfo;
    var realUrl = '../mine/mine#publisherId@' + videoInfo.userId;
    if (userInfo.id == '' || userInfo.id == undefined) {
      wx.navigateTo({
        url: '../userLogin/userLogin?realUrl=' + realUrl,
      })
    } else {
      wx.navigateTo({
        url: '../mine/mine?publisherId=' + videoInfo.userId,
      })

    }
  },
  shareMe:function(){
    var me = this;
    var user = app.getGlobalUserInfo();
    wx.showActionSheet({
      itemList: ["下载到本地","举报用户","分享到好友"],
      success:function(res){
        if (res.tapIndex==0){
          // 下载
          wx.showLoading({
            title: '下载中...',
          })
          wx.downloadFile({
            url: app.serverUrl + me.data.videoInfo.videoPath,
            success: function (res) {
              // 只要服务器有响应数据，就会把响应内容写入文件并进入 success 回调，业务需要自行判断是否下载到了想要的内容
              if (res.statusCode === 200) {
                console.log(res.tempFilePath);

                wx.saveVideoToPhotosAlbum({
                  filePath: res.tempFilePath,
                  success: function (res) {
                    console.log(res.errMsg)
                    wx.hideLoading();
                  }
                })
              }
            }
          })

        } else if (res.tapIndex==1){
          // 举报
          var videoInfo = JSON.stringify(me.data.videoInfo);
          var realUrl = '../videoInfo/videoInfo#videoInfo@' + videoInfo;

          if (user == null || user == undefined || user == '') {
            wx.navigateTo({
              url: '../userLogin/userLogin?realUrl=' + realUrl,
            })
          } else {
            var publishUserId = me.data.videoInfo.userId;
            var videoId = me.data.videoInfo.id;
            var currentUserId = user.id;
            wx.navigateTo({
              url: '../report/report?videoId=' + videoId + "&publishUserId=" + publishUserId
            })
          }

        } else{
         
        }
      }
    })
  },
  onShareAppMessage: function (res) {

    var me = this;
    var videoInfo = me.data.videoInfo;

    return {
      title: '短视频内容分析',
      path: "pages/videoinfo/videoinfo?videoInfo=" + JSON.stringify(videoInfo),
      imageUrl: "https://developers.weixin.qq.com/miniprogram/introduction/image/a.png?t=18090718"
    }
  },

  leaveComment: function () {
    this.setData({
      commentFocus: true
    });
  },

  replyFocus: function (e) {
    var fatherCommentId = e.currentTarget.dataset.fathercommentid;
    var toUserId = e.currentTarget.dataset.touserid;
    var toNickname = e.currentTarget.dataset.tonickname;

    this.setData({
      placeholder: "回复  " + toNickname,
      replyFatherCommentId: fatherCommentId,
      replyToUserId: toUserId,
      commentFocus: true
    });
  },

  saveComment: function (e) {
    var me = this;
    var content = e.detail.value;

    // 获取评论回复的fatherCommentId和toUserId
    var fatherCommentId = e.currentTarget.dataset.replyfathercommentid;
    var toUserId = e.currentTarget.dataset.replytouserid;

    var user = app.getGlobalUserInfo();
    var videoInfo = JSON.stringify(me.data.videoInfo);
    var realUrl = '../videoinfo/videoinfo#videoInfo@' + videoInfo;

    if (user == null || user == undefined || user == '') {
      wx.navigateTo({
        url: '../userLogin/login?redirectUrl=' + realUrl,
      })
    } else {
      wx.showLoading({
        title: '请稍后...',
      })
      wx.request({
        url: app.serverUrl + '/video/saveComment?fatherCommentId=' + fatherCommentId + "&toUserId=" + toUserId,
        method: 'POST',
        header: {
          'content-type': 'application/json', // 默认值
          'headerUserId': user.id,
          'headerUserToken': user.userToken
        },
        data: {
          fromUserId: user.id,
          videoId: me.data.videoInfo.id,
          comment: content
        },
        success: function (res) {
          console.log(res.data)
          wx.hideLoading();

          me.setData({
            contentValue: "",
            commentsList: []
          });

          me.getCommentsList(1);
        }
      })
    }
  },

  // commentsPage: 1,
  //   commentsTotalPage: 1,
  //   commentsList: []

  getCommentsList: function (page) {
    var me = this;

    var videoId = me.data.videoInfo.id;

    wx.request({
      url: app.serverUrl + '/video/getVideoComments?videoId=' + videoId + "&page=" + page + "&pageSize=5",
      method: "POST",
      success: function (res) {
        console.log(res.data);

        var commentsList = res.data.data.rows;
        var newCommentsList = me.data.commentsList;

        me.setData({
          commentsList: newCommentsList.concat(commentsList),
          commentsPage: page,
          commentsTotalPage: res.data.data.total
        });
      }
    })
  },

  onReachBottom: function () {
    var me = this;
    var currentPage = me.data.commentsPage;
    var totalPage = me.data.commentsTotalPage;
    if (currentPage === totalPage) {
      return;
    }
    var page = currentPage + 1;
    me.getCommentsList(page);
  }
})
