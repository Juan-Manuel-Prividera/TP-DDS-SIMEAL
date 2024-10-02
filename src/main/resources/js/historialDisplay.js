function toggleDetalle(id) {
    const detalle = document.getElementById(id);
    if (detalle.style.display === "none") {
        detalle.style.display = "block";
    } else {
        detalle.style.display = "none";
    }
}