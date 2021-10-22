<template>
  <div style="width: 100%; margin: 0 auto;">
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
      <div class="bottom-tip">{{ bottomTip }}</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      bottomTip: "",
      //是否为手机端
      isMobile: null,
      keyword: this.$route.params.keyword,
      posts: [],
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
  mounted() {
    window.addEventListener("scroll", this.handleScroll, true);
  },
  watch: {
    isMobile(newValue) {
      //根据分辨率设置分页样式
      if (newValue) {
        this.page_layout = "prev, pager, next";
      } else {
        this.page_layout = "prev, pager, next, jumper";
      }
    },
  },
  methods: {
    handleScroll() {
      //滚动条距离顶部的距离
      let scrollTop =
        document.body.scrollTop || document.documentElement.scrollTop;
      //窗口高度
      let windowHeight =
        document.documentElement.clientHeight || document.body.clientHeight;
      //页面高度
      let scrollHeight =
        document.documentElement.scrollHeight || document.body.scrollHeight;
      if (windowHeight + scrollTop + 10 >= scrollHeight) {
        this.onScrollBottom();
      }
    },
    //函数节流
    throttle(fn, interval = 300) {
      let canRun = true;
      return function () {
        if (!canRun) return;
        canRun = false;
        setTimeout(() => {
          fn.apply(this);
          canRun = true;
        }, interval);
      };
    },
    //页面滑动到底部事件
    onScrollBottom() {
      if (this.page_total > this.page_size * this.page_index) {
        this.bottomTip = "加载中...";
        this.throttle(() => {
          this.addTableList();
          this.bottomTip = "";
        })();
      } else {
        this.bottomTip = "没有更多内容了";
      }
    },
    addTableList() {
      this.page_index += 1;
      this.getTableList();
    },
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
          this.posts = this.posts.concat(data.data);
          this.getSearchTotal();
        });
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
.container{
  max-width: 1113px;
  margin: 0 auto;
}
.bottom-tip {
  font-size: 14px;
  text-align: center;
  color: #999;
  margin-bottom: 20px;
  font-weight: bold;
}
</style>
