<template>
    <button @click="get">데이터 가져오기</button>
    <p>{{ data }}</p>
  </template>
   
  <script>
  import axios from "axios";
   
  export default {
    name: "Admin",
    data() {
      return {
        data: [],
        id: 1,
      };
    },
    methods: {
      get() {
        axios
          .get(`https://jsonplaceholder.typicode.com/todos/${this.id}`)
          .then((result) => {
            console.log(result.data);
            this.data.push(result.data);
            this.id++;
          })
          .catch((error) => {
            console.log(error);
          })
          .finally(() => {
            console.log("finally");
          });
      },
    },
  };
  </script>
   
  <style></style>