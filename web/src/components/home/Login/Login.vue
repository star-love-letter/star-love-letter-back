<template>
  <div class="login_container" v-title data-title="登录 — 星愿墙">
    <div class="login_box">
      <!--   头像区域   -->
      <div class="avatar_box">
        <img src="@/assets/img/avatar.jpg" alt="">
      </div>
      <!--   登录表单区域   -->
      <el-form ref="loginFromRef" :model="loginFrom" :rules="loginFromRules" class="e_form">
        <!--    用户名    -->
        <el-form-item prop="email">
          <el-input v-focus @keyup.enter.native="login" v-model="loginFrom.email_or_id" prefix-icon="fas fa-user"></el-input>
        </el-form-item>
        <!--    密码    -->
        <el-form-item prop="password">
          <el-input type="password" @keyup.enter.native="login" v-model="loginFrom.password"
                    prefix-icon="fas fa-lock"></el-input>
        </el-form-item>
        <!--    按钮区域    -->
        <el-form-item class="btns">
          <el-button type="primary" @click="login">登录</el-button>
          <el-button type="info" @click="resetLoginFrom">重置</el-button>
          <el-button type="info" @click="goRegister">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    if (!this.isNull(this.getToken())) {
      this.$router.push('./TableList')
    }
  },
  data() {
    return {
      // 这是登录表单的数据绑定对象
      // 只需要通过 rules 属性传入约定的验证规则，并将 Form-Item 的 prop 属性设置为需校验的字段名即可。
      loginFrom: {
        email_or_id: '',
        password: ''
      },
      // 表单验证
      loginFromRules: {
        email_or_id: [
          {required: true, message: '请输入用户名或邮箱', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      },
      token: ''
    }
  },
  methods: {
    goRegister() {
      this.$router.push('/Register')
    },
    // 点击重置按钮，重置登录表单
    resetLoginFrom() {
      this.$refs.loginFromRef.resetFields()
    },
    // 登录
    login() {
      // 获取表单的ref 也就是它的注册信息
      // validate接收一个回调函数 从而拿到验证结果 它的返回值为布尔值
      this.$refs.loginFromRef.validate(async (valid) => {
        // 如果valid为假 说明验证结果错误
        if (!valid) {
          this.$message.error('账号或密码格式错误 请重新输入')
          this.$refs.loginFromRef.resetFields()
          return
        }

        await this.$http.post('/api/user/login', {
          id: this.loginFrom.email_or_id,
          password: this.loginFrom.password
        }).then((data) => {
          this.setToken(data.data)
          this.$message.success(data.message);
          this.$router.push('./TableList')
        })

      })
    },
  },
  // 使用directives注册v-focus全局指令
  directives: {
    focus: {
      inserted: function (el) {
        el.querySelector('input').focus()
      }
    }
  }
}
</script>

<style Lang="less" scoped>
.login_container {
  background-color: #FF7F50;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 20px;
  display: flex;
  justify-content: center;
  position: relative;
}

.avatar_box {
  height: 130px;
  width: 130px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding: 7px;
  box-shadow: 0 0 10px #999;
  position: absolute;
  top: -60px;
  background-color: #fff;
}

.avatar_box img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}

.e_form {
  width: 70%;
  margin-top: 22%;
}

.btns {
  display: flex;
  justify-content: center;
}
</style>
