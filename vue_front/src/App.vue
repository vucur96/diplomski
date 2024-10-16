<script setup lang="ts">
import { RouterView } from 'vue-router'
import MenuComponent from './views/menu.vue';
</script>

<template>
    <div>

        <main class="container">
            <RouterView />
        </main>

        <footer>
            <p>&copy; 2024 My Vue App</p>
        </footer>
    </div>
</template>

<style>
header {
  background-color: #f8f9fa;
  padding: 1em;
  text-align: center;
}

footer {
  background-color: #f8f9fa;
  padding: 1em;
  text-align: center;
  margin-top: 2em;
}
</style>

