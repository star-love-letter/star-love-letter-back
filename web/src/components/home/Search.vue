<template>
  <div style="width: 100%; margin: 0px auto;">
    <div class="container">
      <ul :data="posts" v-masonry>
        <li
          v-for="item in posts"
          :key="item.id"
          v-masonry-tile
          style="margin-left: 0.5rem;width: 360px"
        >
          <Table :item="item" :is-detail="false"> </Table>
        </li>
      </ul>
      <div class="block">
        <el-pagination
          :page-size="page_size"
          :pager-count="5"
          @current-change="TableListChange"
          :current-page="page_index"
          :layout="page_layout"
          :total="page_total"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //是否为手机端
      isMobile: null,
      keyword: this.$route.params.keyword,
      posts: {},
      // 当前页
      page_index: 1,
      // 每个页面的内容数量
      page_size: 30,
      // 查询帖子数量
      page_total: 0,
      // 分页样式
      page_layout: "",
    };
  },
  created() {
    //获取当前是否为手机端
    this.isMobile = this.getIsMobile();

    this.search();
  },
  watch: {
    isMobile(newValue, oldValue) {
      //根据分辨率设置分页样式
      if (newValue) {
        this.page_layout = "prev, pager, next";
      } else {
        this.page_layout = "prev, pager, next, jumper";
      }
    },
  },
  methods: {
    //打开详情页
    goTableDetail(id) {
      this.$router.push({
        name: "TableDetail",
        params: { id: id },
      });
    },
    // 获取搜索数据
    async search() {
      await this.$http
        .get("/api/table/searchList", {
          keyword: this.keyword,
          pageIndex: this.page_index,
          pageSize: this.page_size,
        })
        .then((data) => {
          this.posts = data.data;
          this.getSearchTotal();
        });
    },
    // 页面改变时触发
    TableListChange(newpage) {
      this.page_index = newpage;
      this.search();
    },
    // 查询帖子数量
    async getSearchTotal() {
      await this.$http
        .get("/api/table/searchCount", {
          keyword: this.keyword,
        })
        .then((data) => {
          this.page_total = data.data;
        });
    },
  },
};
</script>

<style src="../../assets/css/posts.css"></style>
<style scoped>
.post{
  width: 360px;
}
.container{
  max-width: 1113px;
  margin: 0 auto;
}
</style>
