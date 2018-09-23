const app = getApp()

Page({
  data: {
    realUrl:''
  },
  onLoad:function(params){
    var realUrl = params.realUrl;
    var me = this;
    realUrl = realUrl.replace(/#/g,"?");
    realUrl = realUrl.replace(/@/g, "=");
    me.setData({
      realUrl: realUrl
    })
  },
  doLogin: function (e) {
    var formObject = e.detail.value;
    var username = formObject.username;
    var password = formObject.password;
    var me = this;
    // 简单验证
    if (username.length == 0 || password.length == 0) {
      wx.showToast({
        title: '用户名或密码不能为空',
        icon: 'none',
        duration: 3000
      })
    } else {
      wx.showLoading({
        title: '正在登录中。。。'
      });
      wx.request({
        url: app.serverUrl + "/login",
        method: "POST",
        data: {
          username: username,
          password: password
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: function (res) {
          console.log(res.data);
          
          var status = res.data.status;
          wx.hideLoading();
          if (status == 200) {
            wx.showToast({
              title: "用户登陆成功~！",
              icon: 'none',
              duration: 3000
            })
            // app.userInfo = res.data.data;
            app.setGlobalUserInfo(res.data.data);

            var realUrl = me.data.realUrl;

            if (realUrl != null && realUrl != '' && realUrl != undefined){
              wx.redirectTo({
                url: realUrl,
              })
            }else{
              wx.redirectTo({
                url: '../mine/mine',
              })
            }


           

          } else if (status == 500) {
            wx.showToast({
              title: res.data.msg,
              icon: 'none',
              duration: 3000
            })
          }
        }
      })
    }
  },
  goRegisterPage: function (e) {
    wx.redirectTo({
      url: '../userRegister/userRegister',
    })
  }
})