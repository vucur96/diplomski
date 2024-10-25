<script setup lang="ts">
import { RouterView } from 'vue-router'
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

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}
th {
  background-color: #1f83a1;
  color: white;
  padding: 10px;
  text-align: left;
  cursor: pointer;
}

th:hover {
  background-color: #36759e;
}

td {
  padding: 10px;
  border: 1px solid #ddd;
  text-align: left;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

tr:hover {
  background-color: #ddd;
}

input[type='text'] {
  padding: 8px;
  margin-bottom: 10px;
  width: 100%;
  box-sizing: border-box;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.btn-secondary {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin-top: 10px;
  cursor: pointer;
  border-radius: 4px;
}

.btn-secondary:hover {
  background-color: #45a049;
}
</style>