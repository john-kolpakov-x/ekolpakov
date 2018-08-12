<template>
  <div id="blog">
    blog id = {{blogId}}
    <table v-if="!isWaiting">
      <tr v-for="r in commentList" :key="r.id">
        <td>{{r}}</td>
      </tr>
    </table>
    <div v-if="isWaiting">
      WAITING...
    </div>
  </div>
</template>

<script lang="ts">
  import {Component, Prop, Vue} from 'vue-property-decorator';
  import * as blog from "../store/blog";

  @Component
  export default class Blog extends Vue {
    @Prop() private blogId!: string;

    async created() {
      await blog.dispatchReset(this.$store);
      await blog.dispatchLoadPage(this.$store);
    }

    get commentList() {
      return blog.readCommentList(this.$store);
    }

    get isWaiting() {
      return blog.readIsWaiting(this.$store);
    }
  }
</script>
