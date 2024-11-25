document.getElementById("confirmarBtn").addEventListener("click", function() {
    // Obtén el valor de data-ofertaId del botón
    const ofertaId = this.getAttribute("data-ofertaId");

    console.log("Antes del fetch con ofertaId = %v", ofertaId);
    // Realiza la solicitud POST con fetch
    fetch("../ofertas/comprar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: ofertaId
    })
        .then(response => {
            if (response.status === 400) {
                window.location.href = "/ofertas?confirmacionCompra=true";  // Redirigir al cliente
            } else if (response.status === 404){
                window.location.href = "/ofertas?confirmacionCompra=false";  // Redirigir al cliente
            } else{
                window.location.href = "/";
            }
        })
        .catch(error => console.error("Error en la solicitud:", error));
});