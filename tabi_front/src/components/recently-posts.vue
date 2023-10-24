<template>
  <ul id="recently-posts">
    <li><h1 class="item-title">최근에 작성한 게시글</h1></li>
    <li>
      <table>
        <tr>
          <td><span>#tag1</span><span>#tag2</span><span>#tag3</span></td>
          <td align="right"><a>다음 여행 계획</a></td>
        </tr>
        <tr>
          <td colspan="2">#2 여행계획</td>
        </tr>
        <tr>
          <td colspan="2"><a>태그</a></td>
        </tr>
        <tr>
          <td colspan="2">#3 여행계획</td>
        </tr>
        <tr>
          <td colspan="2"><a>태그</a></td>
        </tr>
        <tr>
          <td colspan="2">#4 여행계획</td>
        </tr>
        <tr>
          <td colspan="2"><a>태그</a></td>
        </tr>
        <tr>
          <td colspan="2">#5 여행계획</td>
        </tr>
        <tr>
          <td colspan="2"><a>태그</a></td>
        </tr>
      </table>
    </li>
  </ul>
</template>

<style scoped>
.item-title {
  margin-bottom: 20px;
}

#recently-posts {
  margin-bottom: 40px;
  display: block;
}

#recently-posts li table {
  width: 100%;
  padding: 40px;
  border: 2px solid #d8dbe1;
  border-radius: 14px;
  margin: 0 auto;
}
</style>
