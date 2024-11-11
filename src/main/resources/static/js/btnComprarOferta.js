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
            if (response.ok) {
                console.log("Compra procesada, redirigiendo...");
                // Redirigir a la ruta de ofertas después de procesar el POST
                window.location.href = "/ofertas";  // Redirige al usuario a la página de ofertas
            } else {
                console.error("Error en la compra");
            }
        })
        .catch(error => {
            console.error("Error en la solicitud:", error);
        });
    console.log("Después del fetch");
});