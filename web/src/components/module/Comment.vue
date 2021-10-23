<template>
  <div class="comment">
    <a href="/">{{ item.userPublic.name }}：</a>
    <div style="margin: 20px 0 0 20px;">
      <div class="comment-content">
        {{ item.content }}
      </div>
      <el-image
        :key="img"
        v-for="img in this.imgList"
        style="width: 100px; height: 100px"
        :src="imagePath + img"
        fit="cover"
      ></el-image>
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
    };
  },
  props: ["item"],
  created() {
    this.getImgList();
  },
  methods: {
    // 解析图片列表
    getImgList() {
      if (!this.item.images) return;
      this.imgList = JSON.parse(this.item.images);
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
</style>
