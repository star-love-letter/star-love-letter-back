<template>
  <div class="comment">
    <a href="/">{{ item.userPublic.name }}：</a>
    <div style="margin: 20px 0 0 20px;">
      <div class="comment-content">
        {{ item.content }}
      </div>
      <div class="img-box">
        <el-image
          style="width: 150px; height: 150px"
          v-for="(item,index) in imgList"
          :key="index"
          :src="item"
          fit="cover"
          :preview-src-list="imgList"
        >
        </el-image>
      </div>
      <div class="comment-createTime">
        {{ toDates(item.createTime) }}
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        imagePath: axios.defaults.baseURL + "/api/file/image/",
        imgList: [],
        imgVisible: false
      };
    },
    props: ["item"],
    created() {
      this.getImgList();
    },
    methods: {
      // 解析图片列表
      getImgList() {
        if (!this.item.images) {
          return
        }
        // 解析json
        let imgSrc = JSON.parse(this.item.images);

        for (let i = 0; i < imgSrc.length; i++) {
          this.imgList.push(this.imagePath + imgSrc[i]);
        }
      },
    },
  };
</script>

<style>
  .comment {
    background-color: #fff;
    padding: 20px;
    border-bottom: 1px solid #e8e8e8;
  }

  .comment a {
    text-decoration: none;
    font-weight: bold;
    color: #3b6fa8;
    margin-bottom: 10px;
  }

  .comment-content {
    margin-bottom: 10px;
  }

  .comment-createTime {
    margin-top: 10px;
    font-size: 14px;
    color: #999;
  }

  .img-box {
    display: inline-block;
    margin-left: 10px;
  }

  .img-box:first-child {
    margin: 0;
  }
</style>
