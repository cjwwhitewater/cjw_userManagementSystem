<template>
  <div id="body">
    <h1>用户管理</h1>
    <form @submit.prevent="addUser">
      <span>添加用户：</span>
      <input v-model="user.name" placeholder="Name" required />
      <input v-model="user.email" placeholder="Email" required />
      <input v-model="user.age" type="number" placeholder="Age" />
      <button type="submit">{{ user.id ? '更新用户' : '创建用户' }}</button>
    </form>
    <span>搜索用户：</span>
    <input v-model="search" placeholder="搜索用户" @input="fetchUsers" />
    <div v-if="isEditing">
      <h2>{{ editingUser ? '编辑用户' : '新增用户' }}</h2>
      <input v-model="editingUser.name" placeholder="姓名" />
      <input v-model="editingUser.email" placeholder="邮箱" />
      <input v-model="editingUser.age" placeholder="年龄" />
      <button @click="saveUser">保存</button>
    </div>
    <div id="users">
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>姓名</th>
          <th>邮箱</th>
          <th>年龄</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.age }}</td>
          <td>
            <button @click="editUser(user)">编辑</button>
            <button @click="deleteUser(user.id)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
    <div>
      <button @click="prevPage" :disabled="!page">PreviousPage</button>
      <span>Page:{{ page }}</span>
      <button @click="nextPage">NextPage</button>
    </div>

    <div>
      <span>当前页容量：{{ size }}</span>
      <input v-model="newsize" placeholder="页容量">
      <button @click="changSize(newsize)">修改页容量</button>
    </div>
    
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      users: [],
      search: '',
      page: 0,
      size: 10,
      isEditing: false,
      editingUser: null,
      user:{
        name: '',
        email: '',
        age: null,
      }
    };
  },
  methods: {
    fetchUsers() {
      axios.get(`http://localhost:8080/api/user?name=${this.search}&page=${this.page}&size=${this.size}`)
        .then(response => {
          this.users = response.data.content;
        });
    },
    addUser() {
      axios.post('http://localhost:8080/api/user', this.user)
        .then(() => {
          this.fetchUsers();
          this.user = { name: '', email: '', age: null };
        });
    },
    changSize(size){
      this.size=size;
      this.fetchUsers();
    },
    nextPage() {
      this.page++;
      this.fetchUsers();
    },
    prevPage() {
      this.page--;
      this.fetchUsers();
    },
    editUser(user) {
      this.isEditing = true;
      this.editingUser = { ...user };
    },
    saveUser() {
      if (this.editingUser.id) {
        axios.put(`http://localhost:8080/api/user/${this.editingUser.id}`, this.editingUser)
          .then(() => {
            this.isEditing = false;
            this.fetchUsers();
          });
      } else {
        axios.post('http://localhost:8080/api/user', this.editingUser)
          .then(() => {
            this.isEditing = false;
            this.fetchUsers();
          });
      }
    },
    deleteUser(id) {
      axios.delete(`http://localhost:8080/api/user/${id}`)
        .then(() => {
          this.fetchUsers();
        });
    },
  },
  mounted() {
    this.fetchUsers();
  },
};
</script>

<style>
table{
  margin: auto;
  width: 100%;
  background-color: blanchedalmond;
  border-collapse: collapse;
  border :1px solid black;
}
td{
  border-bottom: 1px solid #ddd;
  height: 10px; 
  line-height: 20px
}
th,td{
  text-align: left;
}
tr:hover {
background-color: #f5f5f5;
}
#body{
  background-color :deepskyblue;
}
tr{
  background-color:burlywood;
}
#users{
  left: 50%;
  top: 50%;
  font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
}
button{
  width: 110px;
  height: 30px;
  padding: 5px;
  margin: 5px;
  box-shadow: 0 9px #999;
  border-radius: 15px;
  background-color :#666 ;
  color:wheat;
}

.button:hover {background-color: #3e8e41}

.button:active {
background-color: #0db5ed;
box-shadow: 0 5px #666;
transform: translateY(4px);
}
</style>
