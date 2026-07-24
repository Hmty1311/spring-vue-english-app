<script setup lang="ts">
import { useRouter } from "vue-router";

interface Props {
    title: string;
    backTo?: string;
    showBack?: boolean;
    showMenu?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
    showBack: true,
    showMenu: true
});

const router = useRouter();

const goBack = () => {

    if (props.backTo) {
        router.push(props.backTo);
        return;
    }

    router.back();
};

const goMenu = () => {
    router.push("/mode");
};
</script>

<template>
    <header class="page-header">

        <div class="header-left">

            <button
                v-if="showBack"
                type="button"
                class="icon-button"
                @click="goBack"
            >
                ←
            </button>

        </div>

        <div class="header-center">

            <h1 class="page-title">
                {{ title }}
            </h1>

        </div>

        <div class="header-right">

            <button
                v-if="showMenu"
                type="button"
                class="icon-button"
                @click="goMenu"
            >
                🏠
            </button>

        </div>

    </header>
</template>

<style scoped>
.page-header {

    display: grid;

    grid-template-columns:
        80px
        1fr
        80px;

    align-items: center;

    padding: 16px 24px;

    background: white;

    border-bottom: 1px solid #ececec;

    position: sticky;

    top: 0;

    z-index: 100;
}

.header-left {

    display: flex;

    justify-content: flex-start;
}

.header-center {

    display: flex;

    justify-content: center;
}

.header-right {

    display: flex;

    justify-content: flex-end;
}

.page-title {

    margin: 0;

    font-size: 26px;

    font-weight: 700;
}

.icon-button {

    width: 48px;

    height: 48px;

    border-radius: 50%;

    border: none;

    cursor: pointer;

    font-size: 22px;

    transition: .2s;
}

.icon-button:hover {

    transform: scale(1.08);

    background: #58CC02;

    color: white;
}
</style>