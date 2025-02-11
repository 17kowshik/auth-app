const backendUrl = "http://localhost:8080/users";

function registerUser() {
    const username = document.getElementById("reg-username").value;
    const password = document.getElementById("reg-password").value;

    fetch(`${backendUrl}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userName: username, password: password })
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        } else {
            return response.text().then(error => { throw new Error(error) });
        }
    })
    .then(responseBody => {
        alert(responseBody);
        window.location.href = "index.html";
    })
    .catch(error => alert(error.message));
}

function loginUser() {
    const username = document.getElementById("login-username").value;
    const password = document.getElementById("login-password").value;

    fetch(`${backendUrl}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userName: username, password: password })
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        } else {
            return response.text().then(error => { throw new Error(error) });
        }
    })
    .then(responseBody => {
        alert(responseBody);
        window.location.href = "dashboard.html";
    })
    .catch(error => alert(error.message));
}

function logout() {
    localStorage.removeItem("username");
    window.location.href = "index.html";
}