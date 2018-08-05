<template>
  <div id="blog">
    blog id = {{blogId}}
  </div>
</template>

<script lang="ts">
  import {Component, Prop, Vue} from 'vue-property-decorator';

  @Component
  export default class Blog extends Vue {
    @Prop() private blogId!: string;

  }
</script>
