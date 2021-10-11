<template>
  <div style="width: 100%;margin: 0 auto;">
    <div class="container" style="max-width: 1113px">
      <ul :data="posts" v-masonry>
        <li v-for="(item,index) in posts" :key="item.id" v-masonry-tile style="margin-left: 0.5rem;">
          <Table :item="item" :is-detail="false">
          </Table>
        </li>
        <li>
          <div class="bottom-tip">{{bottomTip}}</div>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      //是否为手机端
      isMobile: null,
      type: 1,
      // 当前页
      page_index: 1,
      // 每个页面的内容数量
      page_size: 10,
      // 总条目数
      page_total: 0,
      // 分页样式
      page_layout: '',
      // 后端数据存放在这里
      posts: [],
      genders: '',
      LoadingStatus: false,
      immediate: false,
      bottomTip:''
    }
  },
  created() {
    //获取当前是否为手机端
    this.isMobile = this.getIsMobile()

    this.getTableTotal()
    this.getTableList()
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll, true)
  },
  watch: {
    isMobile(newValue, oldValue) {
      //根据分辨率设置分页样式
      if (newValue) {
        this.page_layout = 'prev, pager, next'
      } else {
        this.page_layout = 'prev, pager, next, jumper'
      }
    }
  },
  methods: {
    handleScroll() {
      //滚动条距离顶部的距离
      let scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
      //窗口高度
      var windowHeight = document.documentElement.clientHeight || document.body.clientHeight;
      //页面高度
      var scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;
      if ((windowHeight + scrollTop + 10) >= scrollHeight) {
        this.onScrollBottom()
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
      }
    },
    //页面滑动到底部事件
    onScrollBottom() {
      if (this.page_total > this.page_size * this.page_index) {
        this.bottomTip = "加载中...";
        this.throttle(()=>{
          this.addTableList();
          this.bottomTip = "";
        })()
      }else{
        this.bottomTip = "没有更多内容了";
      }
    },
    addTableList(){
      this.page_index += 1
      this.getTableList()
    },
    //获取帖子列表
    async getTableList() {
      await this.$http.get('/api/table/pageList', {
        pageIndex: this.page_index,
        pageSize: this.page_size
      }).then((data) => {
        this.posts = this.posts.concat(data.data)
        console.log(this.posts)
      })
    },
    // 获取帖子总数量
    async getTableTotal() {
      await this.$http.get('/api/table/count').then((data) => {
        this.page_total = data.data
      })
    }
  }
}
</script>

<!--<style src="../../assets/css/posts.css"></style>-->
<style scoped>
.container {
  margin: 0 auto;
}

.bottom-tip {
  font-size: xxx-large;
}
</style>
