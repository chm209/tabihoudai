<template>
  <ul id="banner-container">
    <li><h1 class="item-title">추천하는 관광 도시</h1></li>
    <li id="banner">
      <v-carousel
        cycle
        height="400"
        hide-delimiter-background
        show-arrows="hover"
        id="slide"
      >
        <v-carousel-item v-for="(slide, i) in slides" :key="i">
          <v-sheet :color="colors[i]" height="100%">
            <div class="d-flex fill-height justify-center align-center">
              <div class="text-h2">{{ slide }} Slide</div>
            </div>
            <div
              class="v-carousel__controls"
              style="background-color: rgba(0, 0, 0, 0.3)"
            ></div>
          </v-sheet>
        </v-carousel-item>
      </v-carousel>
    </li>
  </ul>
  <ul id="area-selector-container">
    <h1 class="item-title">관광 지역 선택하기</h1>
    <ul id="area-nav">
      <li>규슈</li>
      <li class="item-bar">|</li>
      <li>시코쿠</li>
      <li class="item-bar">|</li>
      <li>주고쿠</li>
      <li class="item-bar">|</li>
      <li>간사이</li>
      <li class="item-bar">|</li>
      <li>주부</li>
      <li class="item-bar">|</li>
      <li>간토</li>
      <li class="item-bar">|</li>
      <li>도호쿠</li>
      <li class="item-bar">|</li>
      <li>홋카이도</li>
    </ul>
    <v-card class="mx-auto" max-width="1130">
      <v-container>
        <v-row dense>
          <v-col v-for="card in cards" :key="card.title" :cols="card.flex">
            <v-card>
              <v-img :src="card.src" class="align-end" height="500px" cover>
                <v-card-title
                  class="text-white"
                  v-text="card.title"
                ></v-card-title>
              </v-img>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  size="small"
                  color="surface-variant"
                  variant="text"
                  icon="mdi-heart"
                ></v-btn>
                <v-btn
                  size="small"
                  color="surface-variant"
                  variant="text"
                  icon="mdi-bookmark"
                ></v-btn>
                <v-btn
                  size="small"
                  color="surface-variant"
                  variant="text"
                  icon="mdi-share-variant"
                ></v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </ul>
  <ul id="trip-plan">
    <li><h1 class="item-title">인기 여행계획</h1></li>
    <li>
      <div id="container">
        <v-card class="mx-auto" id="most-plan">
          <v-img
            src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
            cover
          ></v-img>
          <v-card-text variant="text">
            오사카 투어 플랜 @USER1234 #추천수 어쩌고 저쩌고</v-card-text
          >
        </v-card>
        <ul id="plan-list">
          <table border="0">
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
        </ul>
      </div>
    </li>
  </ul>
  <Posts />
</template>
<style scoped>
.item-title {
  margin-bottom: 20px;
}

#banner-container {
  width: 1130px;
  margin-top: 30px;
  margin-bottom: 30px;
  padding: 0;
  display: block;
}

#banner-container #banner {
  margin-top: 20px;
  display: block;
}

#banner-container #banner #slide {
  border-radius: 14px;
}

#area-selector-container {
  width: 1130px;
  display: block;
  margin-top: 30px;
  margin-bottom: 30px;
  padding: 0%;
}

#area-selector-container #area-nav {
  display: block;
  font-size: 1.2rem;
  font-weight: 600;
  text-align: center;
  margin: 40px 0px;
}

#area-selector-container #area-nav .item-bar {
  margin: 0px 30px;
}

#trip-plan {
  display: block;
  width: 1130px;
  margin-bottom: 30px;
}

#trip-plan #container {
  display: flex;
}

#trip-plan #container #most-plan {
  width: 550px;
  margin-right: 30px;
  border-radius: 14px;
}

#trip-plan #container #plan-list {
  width: 550px;
  padding: 20px;
  border: 2px solid #d8dbe1;
  border-radius: 14px;
}

#trip-plan #container #plan-list table {
  width: 100%;
  height: 100%;
}
</style>
<script>
import Posts from "../components/recently-posts.vue";
export default {
  components: {
    Posts,
  },
  data() {
    return {
      colors: [
        "indigo",
        "warning",
        "pink darken-2",
        "red lighten-1",
        "deep-purple accent-4",
      ],
      slides: ["First", "Second", "Third", "Fourth", "Fifth"],
      cards: [
        {
          title: "Pre-fab homes",
          src: "https://cdn.vuetifyjs.com/images/cards/house.jpg",
          flex: 12,
        },
        {
          title: "Favorite road trips",
          src: "https://cdn.vuetifyjs.com/images/cards/road.jpg",
          flex: 6,
        },
        {
          title: "Best airlines",
          src: "https://cdn.vuetifyjs.com/images/cards/plane.jpg",
          flex: 6,
        },
      ],
      desserts: [
        {
          name: "Frozen Yogurt",
          calories: 159,
        },
        {
          name: "Ice cream sandwich",
          calories: 237,
        },
        {
          name: "Eclair",
          calories: 262,
        },
        {
          name: "Cupcake",
          calories: 305,
        },
      ],
    };
  },
};
</script>
