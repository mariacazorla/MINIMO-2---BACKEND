(function () {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.replace("/");
        return;
    }

    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        if (!payload.sub) throw new Error("Token sin sub");
    } catch (e) {
        console.error("Token inválido:", e);
        localStorage.removeItem("token");
        window.location.replace("/"); // Redirige si el token es inválido
    }
})();