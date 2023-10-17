    'use strict';


    const messageInput = document.getElementById('message-input');
    const messageBox = document.getElementById('message-box');
    const onlineUsers = document.getElementById('online-users');
    const modal = document.getElementById('myModal');
    const modalContent = document.getElementById('modal-content');
    const joinBtn = document.getElementById('join-btn');
    const usernameInput = document.getElementById('username-input');
    const emojiButton = document.getElementById('emoji-button');
    const emojiList = document.getElementById('emoji-list');


   var stompClient = null;
   var username = null;
   var users = [];
// Update the online user list
    function updateOnlineUsers() {
        $.ajax({
            url: '/online-users',
            success: function(response) {
                users = response;
                onlineUsers.innerHTML = '';
                users.forEach(user => {
                    const li = document.createElement('li');
                    li.textContent = user;
                    onlineUsers.appendChild(li);
                });
            },
            error: function(error) {
                console.error('Error:', error);
            },
            async:false
        });
    };


    // Initialize the online user list
    //updateOnlineUsers();

    // Open the join modal
    modal.style.display = 'flex';

    // Handle join button click event
    joinBtn.addEventListener('click', () => {
        username = usernameInput.value;
        if (username.trim() !== '') {
            //users.push(username);
            //updateOnlineUsers();
            modal.style.display = 'none';
            var socket = new SockJS('/ws');
            console.log(socket.value);
            console.log("11111111111111111");
            stompClient = Stomp.over(socket);
            stompClient.connect({},onConnected,onError);
        }
    });

    function onConnected(){
        //subscribe to the public topic
        stompClient.subscribe('/topic/public',onMessageReceived);
        //tell username to the server
        stompClient.send('/app/chatroom.addUser',
            {},
            JSON.stringify({sender:username,type:'JOIN'})
        );
        updateOnlineUsers();
    }

    function onError(){
        alert("Couldn't connect to server");
    }

    function onMessageReceived(payload){
            var message = JSON.parse(payload.body);
            if(message.type === 'JOIN') {
                updateOnlineUsers();
            } else if (message.type === 'LEAVE') {
                updateOnlineUsers();
            } else {
                const messageElement = document.createElement('div');
                messageElement.className = 'message';
                messageElement.innerHTML = `
                                        <span class="username">${message.sender}:</span>
                                        <span class="message-text">${message.messageBody}</span>
                                        `;
                messageBox.appendChild(messageElement);
            }
    }


    // Handle message sending
        messageInput.addEventListener('keydown', function (event) {
            if (event.key === 'Enter') {
                const messageBody = messageInput.value;
                if (messageBody.trim() !== '' && stompClient !== null) {
                    var message = {
                        sender: username,
                        messageBody: messageBody,
                        type: 'CHAT'
                    };
                    stompClient.send("/app/chatroom.sendMessage", {}, JSON.stringify(message));
                    messageInput.value = '';
                }
            }
        });

    emojiButton.addEventListener('click', () => {
                emojiList.style.display = emojiList.style.display === 'none' ? 'block' : 'none';
        });

     emojiList.addEventListener('click', (event) => {
                if (event.target.classList.contains('emoji')) {
                    const emoji = event.target.textContent;
                    messageInput.value += emoji;
                    emojiList.style.display = 'none';
                }
            });