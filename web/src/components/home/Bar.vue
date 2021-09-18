<template>
  <el-container class="home-container">
    <el-header v-if="isMobile==false">
      <span style="margin-right: auto;margin-left: 3rem">星愿墙</span>
      <el-menu class="menu" :default-active="activeIndex" mode="horizontal" @select="handleSelect">
        <el-menu-item class="item" index="/TableList">
          <i class="ico fas fa-clipboard"></i>
          表白墙
        </el-menu-item>
        <el-menu-item class="item" index="/Confession">
          <i class="ico fas fa-heart"></i>
          我要表白
        </el-menu-item>
        <el-menu-item class="item" index="/Help">
          <i class="ico fas fa-question"></i>
          帮助
        </el-menu-item>
      </el-menu>

      <el-input v-model="SearchKeyword" @keyup.enter.native="goSearch" placeholder="请输入你想搜索的数据"
                style="width: 18.75rem;margin-left: 1.25rem">
        <el-button @click="goSearch" slot="append" icon="el-icon-search"></el-button>
      </el-input>

      <div v-if="isLogin" class="userAvatar" @mouseenter="show = !show" @mouseleave="show = !show">
        <!--   用户头像   -->
        <div class="avatar_box">
          <img src="@/assets/img/avatar.jpg" alt="">
          <!--   用户个人信息   -->
        </div>
        <div class="userInfo">
          <transition name="el-fade-in-linear">
            <div v-show="show" class="transition-box">
              <ul>
                <li>
                  <span class="userName">用户名：</span>
                  {{ this.userInfo.name }}
                </li>
                <li>
                  <span>用户邮箱：</span>
                  <div class="text">
                    {{ this.userInfo.email }}
                  </div>
                </li>
                <li>
                  <span>注册账号的时间：</span>
                  <div class="text">
                    {{ this.toDates(this.userInfo.createTime) }}
                  </div>
                </li>
                <li>
                  <span>上次登录的时间：</span>
                  <div class="text">
                    {{ this.toDates(this.userInfo.lastTime) }}
                  </div>
                </li>
              </ul>
            </div>
          </transition>
        </div>
      </div>
      <!-- 退出登录 -->
      <el-button v-if="isLogin" type="primary" round size="small" @click="Logout">退出登录</el-button>
      <!-- 登录用户 -->
      <el-button v-else type="primary" round size="small" @click="Login">登录用户</el-button>

    </el-header>
    <el-header v-else>
      <span style="margin-right: auto;margin-left: 3rem">星愿墙</span>
      <span v-on:click="changeMenuState = !changeMenuState" class="btn-menu el-icon-menu"></span>
    </el-header>
    <transition name="fade">
      <el-row v-if="changeMenuState" class="tac">
        <el-col style="position: absolute;z-index: 3">
          <el-menu
            default-active="2"
            text-color="#fff"
            active-text-color="#ffd04b"
            @select="handleSelect">
            <el-menu-item index="/TableList">
              <i class="ico fas fa-clipboard"></i>
              <span slot="title">表白墙</span>
            </el-menu-item>
            <el-menu-item index="/Confession">
              <i class="ico fas fa-heart"></i>
              <span slot="title">我要表白</span>
            </el-menu-item>
            <el-menu-item index="/Help">
              <i class="ico fas fa-question"></i>
              <span slot="title">Help</span>
            </el-menu-item>
            <el-input v-model="SearchKeyword" @keyup.enter.native="goSearch" placeholder="请输入你想搜索的数据"
                      style="width: 100%;margin-top:0.8rem;margin-bottom:0.4rem">
            </el-input>
          </el-menu>
        </el-col>
      </el-row>
    </transition>
    <el-container>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      userInfo: null,
      show: false,
      //是否为手机端
      isMobile: false,
      activeIndex: '1',
      // 搜索关键词
      SearchKeyword: '',
      //是否展开侧边导航栏
      changeMenuState: false,
      isLogin: false
    }
  },
  created() {
    //获取当前是否为手机端
    this.isMobile = this.getIsMobile();
    //获取路由路径
    this.activeIndex = this.$route.path;
    // 获取用户信息
    this.getUserInfo()
  },
  watch: {
    userInfo(newV,oldV){
        this.isLogin = !this.isNull(newV)
    }
  },
  methods: {
    Login() {
      this.$router.push('/Login');
    },
    Logout() {
      this.$http.post('/api/user/logout').then((response) => {
        this.setToken('')
        this.$message.success('退出登录');
        this.$router.push('/Login');
      })
    },
    //跳转搜索页面
    goSearch() {
      this.$router.push({
        name: 'Search',
        params: {keyword: this.SearchKeyword}
      })
    },
    //跳转导航
    handleSelect(key, keyPath) {
      this.$router.push({
        path: key
      })
    },
    //打开或关闭侧边导航栏
    changeMenuState() {
      this.isMenuState = !this.isMenuState
    },
    //获取用户信息
    async getUserInfo() {

      //当token为空
      if (this.isNull(this.getToken())) {
        this.userInfo = null
        return
      }

      await this.$http.get('/api/user/user', {
        keyword: this.keyword,
        page_index: this.page_index,
        page_size: this.page_size
      }).then((data) => {
        this.userInfo = data.data
      }).catch((res) => {
        this.userInfo = null
      })
    }
  }
}
</script>
<style scoped lang="less">
@import "../../assets/css/Home.css";

.el-header {
  position: relative;
}

.el-input {
  margin-right: 100px;
}

.transition-box {
  width: 230px;
  height: 300px;
  /*background-color: #808ECE;*/
  background-color: #fff;
  position: absolute;
  top: 30px;
  right: -75px;
  border-radius: 10px;
  font-size: 16px;
  color: #555;
  overflow: hidden;

  ul {
    margin-top: 30px;

    li {
      width: 100%;
      border-bottom: 1px solid #e3e3e3;
      padding: 10px 10px 22px 10px;

      span {
        display: block;
        color: #4d6bff;
      }

      .text {
        padding-left: 20px;
        font-size: 14px;
        /*color: rgb(112, 116, 116);*/
        padding-top: 10px;
        text-align: center;
      }

      .userName {
        display: inline-block;

      }
    }

    li:last-child {
      border: 0;
    }
  }
}

.el-button {
  margin-left: 20px;
}

.userAvatar {
  position: relative;
}

.userAvatar:hover .avatar_box {
  height: 80px;
  width: 80px;
  top: -25px;
  right: 0;
}

.avatar_box {
  height: 50px;
  width: 50px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding: 2px;
  box-shadow: 0 0 10px #999;
  background-color: #fff;
  cursor: pointer;
  z-index: 20;
  position: absolute;
  top: -25px;
  right: 10px;
}

.avatar_box img {
  width: 100%;
  border-radius: 50%;
}

.home-container {
  height: 100%;
  margin-right: calc(100% - 100vw);
}

.item, .el-header {
  font-size: 16px;
  background-color: #3B6FA8;
}

.el-aside {
  background-color: #82ADD9;
}

.el-main {
  background-image: linear-gradient(94.3deg, rgba(126, 141, 206, 1) 10.9%, rgba(184, 166, 166, 1) 87.1%);
  display: flex;
  justify-content: center;
  align-items: center;
}

@media screen and (max-width: 1600px) and (min-width: 1000px) {
  .menu {
    border-bottom: none !important;
    margin-left: 1.25rem;
    margin-right: 1.25rem;
  }

  .el-header {
    display: flex;
    align-items: center;
    justify-content: flex-start;
    font-weight: bold;
    font-size: 1.25rem;
    padding-left: 5.5rem;
    padding-right: 5.5rem;
    color: white;
  }

  .el-menu--horizontal > .el-menu-item,
  .el-menu--horizontal > .el-menu-item i {
    color: #ffffff;
  }

  .el-menu--horizontal > .el-menu-item.is-active {
    border-bottom: 2px solid #D88333;
    color: #D88333
  }

  .el-menu--horizontal > .el-menu-item.is-active i {
    color: #D88333
  }

  .el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
  .el-menu--horizontal .el-menu-item:not(.is-disabled):hover,
  .el-menu--horizontal .el-menu-item:not(.is-disabled):hover i {
    outline: 0;
    color: #D88333;
    background-color: #5f79a8;
  }
}

@media screen and (max-width: 1000px) {
  .menu {
    border-bottom: none !important;
    display: flex;
  }

  .el-menu {
    background-color: rgba(84, 92, 100, .95);
  }

  .el-header {
    display: flex;
    align-items: center;
    justify-content: flex-start;
    font-weight: bold;
    font-size: 1.25rem;
    color: white;
  }
}


.item {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
  /* 移入时鼠标变成小手 */
  cursor: pointer;
}

.router-link-active {
  color: #ffc107;
  border-bottom: 0.3rem solid #ffc107;
}

.ico {
  display: inline-block;
  margin-right: 0.2rem;
}

/* 侧边导航栏 */
.btn-menu {
  cursor: pointer;
}

.tac {
  width: 100%;
  position: relative;
}

/* 有bug的动画样式 */
.fade-enter-active, .fade-leave-active {

}

.fade-enter, .fade-leave-to {

}
</style>
