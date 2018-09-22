const app = getApp()

Page({
  data: {
    // 用于分页的属性
    totalPage: 1,
    page: 1,
    videoList: [],
    screenWidth: 350,
    serverUrl: "",
    searchValue:""
  },

  onLoad: function (params) {
    var me = this;
    var screenWidth = wx.getSystemInfoSync().screenWidth;
    me.setData({
      screenWidth: screenWidth,
    });

    var searchValue = params.searchValue;
    var isSaveRecord = params.isSaveRecord;
    if (isSaveRecord == null || isSaveRecord == "" || isSaveRecord == undefined){
      isSaveRecord = 0;
    }

    me.setData({
      searchValue: searchValue,
    });



    // 获取当前的分页数
    var page = me.data.page;
    me.getAllVideosList(page, isSaveRecord);
  },

  getAllVideosList: function (page, isSaveRecord){
    var me = this;
    var serverUrl = app.serverUrl;
    wx.showLoading({
      title: '请等待，加载中...',
    });


    wx.request({
      url: serverUrl + '/video/showAll?page=' + page + "&isSaveRecord =" + isSaveRecord,
      method: "POST",
      data:{
        videoDesc: me.data.searchValue
      },
      success: function (res) {
        wx.hideLoading();
        wx.hideNavigationBarLoading();
        wx.stopPullDownRefresh();

        console.log(res.data);

        // 判断当前页page是否是第一页，如果是第一页，那么设置videoList为空
        if (page === 1) {
          me.setData({
            videoList: []
          });
        }

        var videoList = res.data.data.rows;
        var newVideoList = me.data.videoList;

        me.setData({
          videoList: newVideoList.concat(videoList),
          page: page,
          totalPage: res.data.data.total,
          serverUrl: serverUrl
        });

      }
    })
  },

  onPullDownRefresh: function (params) {
    var me = this;
    wx.showNavigationBarLoading();
    me.getAllVideosList(1,0);

  },

  onReachBottom: function (params){
    var me = this;
    var currentPage = me.data.page;
    var totalPage = me.data.totalPage;
    
    //判断当前页数和总页数是否相等，如果相同已经无需请求
    if (currentPage == totalPage){
      wx.showToast({
        title: '已经没有视频啦~',
        icon:"none"
      })
      return;
    }
    var page = currentPage+1;
    me.getAllVideosList(page,0);

},
  showVideoInfo:function(e){
    var me = this;
    var videoList = me.data.videoList;
    var arrIndex = e.target.dataset.arrindex;
    var videoInfo = JSON.stringify(videoList[arrIndex]);
    wx.redirectTo({
      url: '../videoInfo/videoInfo?videoInfo=' + videoInfo,
    })

  }


})
