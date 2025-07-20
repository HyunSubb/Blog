<template>
  <div>
    <p>버튼을 눌러 숫자를 올려보세요.</p>
    <!-- @click="increment"는 버튼을 클릭할 때마다 아래 script에 있는 increment 함수를 실행시킵니다. -->
    <button class="btn btn-primary" @click="increment">클릭!</button>
    <!-- {{ count }}는 count 변수의 현재 값을 화면에 보여줍니다. -->
    <p>현재 숫자: {{ count }}</p>
  </div>
</template>

<script setup>
// Vue에서 반응형 데이터를 만들기 위한 ref 함수를 가져옵니다.
import { ref } from 'vue';

// ref(0)를 사용해서 0으로 초기화된 반응형 변수 'count'를 선언합니다.
// * 'ref'로 감싸야 Vue가 이 값의 변경을 감지!! * 하고 화면을 자동으로 업데이트할 수 있습니다.
const count = ref(0);

// 버튼을 클릭했을 때 실행될 함수입니다.
const increment = () => {
  // ref로 만든 변수의 실제 값에 접근하려면 .value를 사용해야 합니다.
  count.value++;
  console.log('현재 count 값:', count.value);
};
</script>

<style scoped>
/* 이 컴포넌트에만 적용되는 스타일 */
div {
  padding: 20px;
}
button {
  margin-right: 10px;
}
</style>
