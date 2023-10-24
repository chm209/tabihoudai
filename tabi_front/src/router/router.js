import { createWebHistory, createRouter } from "vue-router";
import Home from "@/views/Home.vue";
import Admin from "@/views/Admin.vue";
import Error from "@/views/Error.vue";
import Login from "@/components/login.vue";
import register from "@/components/register.vue";
import pwInquiry from "@/components/pwInquiry.vue";


const router = createRouter({
    history : createWebHistory(),
    routes : [ // path별 component를 추가한다.
        { path : "/", name : "home", component : Home },
        { path : "/admin", name : "admin", component : Admin},
        { path : "/login", name : "login", component : Login},
        { path : "/register", name : "register", component : register},
        { path : "/pwInquiry", name : "pwInquiry", component : pwInquiry},
        {
            path : "/:pathMatch(.*)",
            name : "not-found",
            component : Error
        },
    ]
});

export default router;