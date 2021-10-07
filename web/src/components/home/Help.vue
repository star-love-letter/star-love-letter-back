<template>
  <div>
    <h2>帮助📭</h2>
    <label>主要功能</label>
    <ol>
      <li>支持微信、邮箱登录✉</li>
      <li>支持微信小程序💖</li>
      <li>支持上传图片📌、邮箱提醒😝</li>
      <li>可以匿名🥰、评论📝、搜索🪧等</li>
    </ol>
    <label>区分男生女生</label>
    <span class="fas fa-mars" style="color: #3B6FA8">男生</span>
    <span class="fas fa-venus" style="color: deeppink">女生</span>
    <span class="fas fa-genderless">保密</span>
    <label>发布表白</label>
    <p>可以设置匿名表白</p>
    <p>支持评论邮箱提醒（需先绑定邮箱）</p>
    <p>最多可以上传9张图片</p>
    <p>支持男男、男女、女男、女女</p>
    <label>发布评论</label>
    <p>最多可以上传6张图片</p>
    <p>不可匿名评论</p>
    <label>搜索帖子</label>
    <p>能同时搜索用户名称、匿名名称、表白内容、表白者名称</p>
    <p>暂不支持搜索评论</p>
    <label>点赞</label>
    <p>每个帖子每个账号只能点一次赞</p>
    <p>可以取消点赞</p>
    <p>以后会开发对赞多的帖子特殊展示</p>

    <h2>开源代码</h2>
    <a href="https://github.com/constStar/wall">GitHub</a>
    <a href="https://gitee.com/constStar/wall">Gitee</a>
    <p>欢迎大家提交issues，同时也欢迎大家二次开发😘</p>

    <h2>项目完善中，敬请期待⭐⭐⭐</h2>
  </div>
</template>


<script>
export default {
  data() {
    return {}
  },
  methods: {},
  mounted() {
  }
}
</script>

<style scoped>

div {
  line-height: 1.5;
  align-self: flex-start;
  padding:1em 5em 5em;
  background-color: #fff;
  border-radius: .6rem;
}

h2 {
  display: block;
  font-size: 1.5em;
  font-weight: bold;
  margin: 1em 0 0.5em 0;
}

label {
  display: block;
  font-weight: bold;
  margin: 1.5em 0 0.5em 0;
}

a {
  display: inline-block;
}

ol, span, p, a {
  text-indent: 1em;
}


</style>
