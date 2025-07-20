<template>
  <div class="container mt-4">
    <h1 class="mb-4">게시글 목록</h1>

<!--    <div v-if="posts.length === 0" class="alert alert-info" role="alert">-->
<!--      게시글이 없습니다. 새로운 게시글을 작성해보세요!-->
<!--    </div>-->

<!--    :class="{ 'text-muted': isPostLoading[post.id] }": isPostLoading[post.id]가 true일 때 해당 요소의 텍스트 색상을-->
<!--    흐리게(muted) 만듭니다. 이는 해당 포스트가 로딩 중임을 시각적으로 사용자에게 알려주는 역할을 합니다.-->
<!--    * :style="{ 'cursor': isPostLoading[post.id] ? 'not-allowed' : 'pointer' }": isPostLoading[post.id]가 true일 때 마우스 커서를-->
<!--    '금지' 모양으로 변경하고, 그렇지 않을 때는 '포인터' 모양으로 변경합니다. 이는 로딩 중인 포스트에 대한 클릭 등의 상호작용을-->
<!--    막거나, 현재 상호작용이 불가능함을 시각적으로 나타냅니다.-->

    <div>
      <div v-for="post in posts" :key="post.id" class="card mb-3">
        <div class="card-body">
          <!-- 클릭 시 moveToRead 함수를 호출하고, 로딩 중일 때는 링크 비활성화 -->
          <h5 class="card-title" 
              :class="{ 'text-muted': isPostLoading[post.id] }"
              :style="{ 'cursor': isPostLoading[post.id] ? 'not-allowed' : 'pointer' }"
              @click="!isPostLoading[post.id] && moveToRead(post.id)">
            {{ post.title }}
            <span v-if="isPostLoading[post.id]" class="spinner-border spinner-border-sm ms-2" role="status" aria-hidden="true"></span>
          </h5>
          <!-- <p class="card-text">{{ post.content }}</p> -->
          <!-- 필요하다면 여기에 더 많은 정보나 버튼을 추가할 수 있습니다. -->
        </div>
      </div>
    </div>

    <!-- 페이지네이션 -->
    <nav aria-label="Page navigation example" class="mt-4">
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{ disabled: currentPage === 1 }">
          <a class="page-link" href="#" @click.prevent="fetchPosts(currentPage - 1)">이전</a>
        </li>
        <li class="page-item" v-for="page in totalPages" :key="page" :class="{ active: page === currentPage }">
          <a class="page-link" href="#" @click.prevent="fetchPosts(page)">{{ page }}</a>
        </li>
        <li class="page-item" :class="{ disabled: currentPage === totalPages }">
          <a class="page-link" href="#" @click.prevent="fetchPosts(currentPage + 1)">다음</a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();

const posts = ref([]);
const isPostLoading = ref({}); // 특정 게시글 로딩 상태를 관리하기 위한 객체
const totalPages = ref(0); // 총 페이지 수
const currentPage = ref(1); // 현재 페이지

onMounted(async () => {
  await fetchPosts(currentPage.value);
});

const fetchPosts = async (page) => {
  try {
    const response = await axios.get(`http://localhost:8080/posts?page=${page}&size=10`);
    posts.value = response.data.content;
    totalPages.value = response.data.totalPages;
    currentPage.value = page;
    console.log('response:', posts.value);
    console.log('게시글 목록:', response.data);
    console.log('총 게시글 수:', response.data.totalElements);
    console.log('총 페이지 수:', response.data.totalPages);
  } catch (error) {
    console.error('게시글을 불러오는 데 실패했습니다:', error);
    alert('게시글 목록을 불러오는 데 실패했습니다. 서버 상태를 확인해주세요.');
  }
};

// 게시글 제목 클릭 시 상세 데이터를 미리 불러온 후 이동
const moveToRead = async (id) => {
  // 1. 이미 해당 게시글의 상세 정보를 불러오는 중이라면, 중복 요청을 방지하기 위해 함수를 종료합니다.
  if (isPostLoading.value[id]) return;

  // 2. 해당 게시글의 로딩 상태를 true로 설정하여 UI에 로딩 중임을 표시합니다.
  isPostLoading.value[id] = true;
  try {
    // 3. 백엔드 API (http://localhost:8080/posts/{id})를 호출하여
    //    클릭한 게시글의 상세 정보를 비동기적으로 미리 불러옵니다.
    // 3. Vue Router의 push 메서드를 사용하여 상세 페이지(ReadView.vue)로 이동합니다.
    //    이때, 게시글의 ID만 파라미터로 전달합니다. 상세 정보는 ReadView에서 직접 불러옵니다.
    router.push({ 
      name: 'read', // router/index.js에 정의된 상세 페이지 라우트의 이름
      params: { id: id } // URL 경로에 포함될 파라미터 (예: /posts/123)
    });
  } catch (error) {
    // 5. 데이터 불러오기 중 오류가 발생하면 콘솔에 에러를 기록하고 사용자에게 알림을 줍니다.
    console.error(`게시글 ${id}의 상세 정보를 불러오는 데 실패했습니다:`, error);
    alert('게시글 상세 정보를 불러오는 데 실패했습니다. 서버 상태를 확인해주세요.');
  } finally {
    // 6. 데이터 불러오기 과정이 성공하든 실패하든, 로딩 상태를 false로 되돌립니다.
    isPostLoading.value[id] = false;
  }
};

// const moveToPage = (page) => {
//
// }
</script>

<style scoped>
.container {
  max-width: 760px; /* 디시인사이드 본문 너비와 유사하게 조정 */
  margin: 40px auto; /* 중앙 정렬 및 상하 여백 */
  padding: 0 10px; /* 좌우 패딩 */
  font-family: 'Malgun Gothic', 'Apple SD Gothic Neo', 'Nanum Gothic', sans-serif; /* 폰트 변경 */
}

h1 {
  text-align: center;
  color: #333; /* 일반적인 텍스트 색상 */
  margin-bottom: 30px;
  font-size: 2rem;
  font-weight: 600;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee; /* 하단 구분선 */
}

.card {
  border: none; /* 카드 자체의 경계선 제거 */
  border-bottom: 1px solid #eee; /* 하단 구분선으로 목록 느낌 */
  border-radius: 0; /* 모서리 둥글기 제거 */
  box-shadow: none; /* 그림자 제거 */
  transition: background-color 0.1s ease-in-out; /* 호버 시 부드러운 전환 */
  margin-bottom: 0; /* 카드 간 간격 제거 (border-bottom으로 대체) */
}

.card:last-child {
  border-bottom: none; /* 마지막 카드는 하단 구분선 제거 */
}

.card:hover {
  background-color: #f9f9f9; /* 호버 시 배경색 변경 */
  transform: none; /* 떠오르는 효과 제거 */
  box-shadow: none; /* 그림자 제거 */
}

.card-body {
  padding: 15px 0; /* 패딩 조정 */
}

.card-title {
  cursor: pointer;
  font-size: 1.1rem; /* 폰트 크기 조정 */
  color: #333; /* 기본 텍스트 색상 */
  line-height: 1.4; /* 줄 간격 */
  margin-bottom: 0;
  font-weight: 500;
  transition: color 0.1s ease-in-out;
}

.card-title:hover {
  color: #007bff; /* 호버 시 강조색 */
  text-decoration: none; /* 밑줄 제거 */
}

.card-title.text-muted {
  cursor: not-allowed;
  color: #999 !important; /* 더 연한 회색 */
}

.spinner-border {
  vertical-align: middle;
  color: #007bff; /* 스피너 색상 */
  width: 1rem;
  height: 1rem;
}
</style>

