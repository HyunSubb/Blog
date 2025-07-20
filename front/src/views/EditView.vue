<template>
  <div class="container mt-4" style="max-width: 800px;">
    
    <!-- 제목 입력란 -->
    <div class="mb-3">
      <!-- form-control 클래스로 Bootstrap의 기본 입력 필드 스타일을 적용합니다. -->
      <input type="text" class="form-control" placeholder="제목을 입력해주세요" v-model="title">
    </div>

    <!-- 내용 입력란 -->
    <div class="mb-3">
      <!-- el-input 대신 textarea를 사용하고, form-control 클래스를 적용합니다. -->
      <textarea class="form-control" rows="10" placeholder="내용을 입력해주세요" v-model="content"></textarea>
    </div>

    <!-- 버튼 영역 -->
    <div class="d-flex justify-content-end">
      <!-- btn, btn-primary 클래스는 이미 적용되어 있습니다. -->
      <button class="btn btn-primary" @click="Edit()">글 수정 완료</button>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();

// 기능 로직 없이, 순수하게 템플릿의 스타일링만 변경했습니다.
const title = ref('');
const content = ref('');

onMounted(() => {
  axios.get(`http://localhost:8080/posts/${route.params.id}`)
    .then(response => {
      title.value = response.data.title;
      content.value = response.data.content;
    })
    .catch(error => {
      console.error("Error fetching post:", error);
      // Optionally, redirect to an error page or home page
    });
})

const Edit = function() {
  // console.log(title.value + "/" + content.value);
  // alert(title.value + "/" + content.value);
  axios.patch(`http://localhost:8080/posts/${route.params.id}`, {
    title: title.value,
    content: content.value
  }).then(() => {
    router.push({
      name: 'read', // router/index.js에 정의된 상세 페이지 라우트의 이름
      params: { id: route.params.id } // URL 경로에 포함될 파라미터 (예: /posts/123)
    });
  })
}

</script>

<style scoped>
/* 이 스타일은 현재 컴포넌트(WriteView.vue)에만 적용됩니다. */
</style>
