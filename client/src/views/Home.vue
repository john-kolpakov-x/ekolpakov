<template>
  <div class="home">
    <!--<img src="../assets/logo.png">-->
    <!--<HelloWorld msg="hi"/>-->
    <Blog blogId="314"/>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import HelloWorld from '@/components/HelloWorld.vue';
  import Blog from '@/components/Blog.vue';

  @Component({
    components: {
      HelloWorld, Blog,
    },
  })
  export default class Home extends Vue {}
</script>
