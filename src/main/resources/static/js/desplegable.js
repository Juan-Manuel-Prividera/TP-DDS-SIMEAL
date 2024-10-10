document.getElementById('btn-expand').addEventListener('click', function() {
    const expandableDiv = document.getElementById('expandible-p' );
    console.log("clikc detectado en btn")
    if (expandableDiv.classList.contains('show')) {
        expandableDiv.classList.remove('show');
        expandableDiv.classList.add('hidden');
    } else {
        expandableDiv.classList.remove('hidden');
        expandableDiv.classList.add('show');

    }
});

// document.querySelectorAll('.div-expand').forEach(button => {
//     button.addEventListener('click', function() {
//         const expandableDiv = button.nextElementSibling;
//         console.log("click detectado en div")
//         if (expandableDiv.classList.contains('show')) {
//             expandableDiv.classList.remove('show');
//             expandableDiv.classList.add('hidden');
//         } else {
//             expandableDiv.classList.remove('hidden');
//             expandableDiv.classList.add('show');
//         }
//     });
// });

