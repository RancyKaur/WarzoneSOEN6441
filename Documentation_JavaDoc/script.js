/*
 * Copyright (c) 2013, 2020, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

var moduleSearchIndex;
var packageSearchIndex;
var typeSearchIndex;
var memberSearchIndex;
var tagSearchIndex;
function loadScripts(doc, tag) {
    createElem(doc, tag, 'search.js');

    createElem(doc, tag, 'module-search-index.js');
    createElem(doc, tag, 'package-search-index.js');
    createElem(doc, tag, 'type-search-index.js');
    createElem(doc, tag, 'member-search-index.js');
    createElem(doc, tag, 'tag-search-index.js');
}

/**
 * Creates and appends an HTML element with a specified 'src' attribute.
 *
 * @param {Document} doc - The document object.
 * @param {string} tagName - The tag name of the element to create.
 * @param {string} path - The path to the script or resource to load.
 */
function createElem(doc, tag, path) {
    // Create a new script element
    var script = doc.createElement(tag);

    // Get the first element of the specified tag type (e.g., the first <script> element)
    var scriptElement = doc.getElementsByTagName(tag)[0];

    // Set the 'src' attribute of the script element
    script.src = pathtoroot + path;
    scriptElement.parentNode.insertBefore(script, scriptElement);
}

/**
 * Shows or hides elements in a table-like layout and updates tabs accordingly.
 *
 * @param {string} tableId - The ID of the table container element.
 * @param {string} selected - The class name of the selected table section.
 * @param {number} columns - The number of columns in the table.
 */
function show(tableId, selected, columns) {
    // Hide all elements with the given tableId that are not selected
    if (tableId !== selected) {
        document.querySelectorAll(`div.${tableId}:not(.${selected})`)
            .forEach(function(elem) {
                elem.style.display = 'none';
            });
    }
    
    // Show and style selected elements
    document.querySelectorAll(`div.${selected}`)
        .forEach(function(elem, index) {
            elem.style.display = '';
            const isEvenRow = index % (columns * 2) < columns;
            const rowColorClass = isEvenRow ? evenRowColor : oddRowColor;
            elem.classList.remove(rowColorClass === evenRowColor ? oddRowColor : evenRowColor);
            elem.classList.add(rowColorClass);
        });
    
    // Update tabs if needed
    updateTabs(tableId, selected);
}


function updateTabs(tableId, selected) {
    document.querySelector('div#' + tableId +' .summary-table')
        .setAttribute('aria-labelledby', selected);
    document.querySelectorAll('button[id^="' + tableId + '"]')
        .forEach(function(tab, index) {
            if (selected === tab.id || (tableId === selected && index === 0)) {
                tab.className = activeTableTab;
                tab.setAttribute('aria-selected', true);
                tab.setAttribute('tabindex',0);
            } else {
                tab.className = tableTab;
                tab.setAttribute('aria-selected', false);
                tab.setAttribute('tabindex',-1);
            }
        });
}

function switchTab(e) {
    var selected = document.querySelector('[aria-selected=true]');
    if (selected) {
        if ((e.keyCode === 37 || e.keyCode === 38) && selected.previousSibling) {
            // left or up arrow key pressed: move focus to previous tab
            selected.previousSibling.click();
            selected.previousSibling.focus();
            e.preventDefault();
        } else if ((e.keyCode === 39 || e.keyCode === 40) && selected.nextSibling) {
            // right or down arrow key pressed: move focus to next tab
            selected.nextSibling.click();
            selected.nextSibling.focus();
            e.preventDefault();
        }
    }
}

var updateSearchResults = function() {};

function indexFilesLoaded() {
    return moduleSearchIndex
        && packageSearchIndex
        && typeSearchIndex
        && memberSearchIndex
        && tagSearchIndex;
}

// Workaround for scroll position not being included in browser history (8249133)
document.addEventListener("DOMContentLoaded", function(e) {
    var contentDiv = document.querySelector("div.flex-content");
    window.addEventListener("popstate", function(e) {
        if (e.state !== null) {
            contentDiv.scrollTop = e.state;
        }
    });
    window.addEventListener("hashchange", function(e) {
        history.replaceState(contentDiv.scrollTop, document.title);
    });
    contentDiv.addEventListener("scroll", function(e) {
        var timeoutID;
        if (!timeoutID) {
            timeoutID = setTimeout(function() {
                history.replaceState(contentDiv.scrollTop, document.title);
                timeoutID = null;
            }, 100);
        }
    });
    if (!location.hash) {
        history.replaceState(contentDiv.scrollTop, document.title);
    }
});
