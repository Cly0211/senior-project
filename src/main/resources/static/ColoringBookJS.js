// Define the available colors
const colors = ['#ff0000', '#00ff00', '#0000ff', '#ffff00', '#ff00ff', '#00ffff'];

// Get the canvas element
const coloringBook = document.getElementById('coloring-book');

// Create the color palette
const colorPalette = document.getElementById('color-palette');

colors.forEach(color => {
    const colorDiv = document.createElement('div');
    colorDiv.classList.add('color');
    colorDiv.style.backgroundColor = color;
    colorDiv.addEventListener('click', () => setColor(color));
    colorPalette.appendChild(colorDiv);
});

// Initialize the selected color
let selectedColor = colors[0];

function setColor(color) {
    selectedColor = color;
}

// Add event listeners to the coloring book canvas
let isDrawing = false;

coloringBook.addEventListener('mousedown', () => isDrawing = true);
coloringBook.addEventListener('mouseup', () => isDrawing = false);
coloringBook.addEventListener('mousemove', draw);

function draw(e) {
    if (!isDrawing) return;

    const rect = coloringBook.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    const dot = document.createElement('div');
    dot.style.position = 'absolute';
    dot.style.backgroundColor = selectedColor;
    dot.style.borderRadius = '50%';
    dot.style.width = '10px';
    dot.style.height = '10px';
    dot.style.left = x + 'px';
    dot.style.top = y + 'px';

    coloringBook.appendChild(dot);
}

// Add a "Clear" button to reset the coloring book
const clearButton = document.createElement('button');
clearButton.innerText = 'Clear';
clearButton.addEventListener('click', clearCanvas);
colorPalette.appendChild(clearButton);

function clearCanvas() {
    coloringBook.innerHTML = ''; // Clear all colored dots
}

// Add more features and improvements as needed
