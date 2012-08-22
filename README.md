# Base Code

This is my Base Code project for [Ludum Dare](http://www.ludumdare.com/compo/) #24.

## Status of the code

This code simply displays a shape. When the user clicks on the canvas
element, the shape will start gradually falling down the screen (one
30-pixel move every 0.5seconds). That's it. No other game functionality.

## Requirements

* [Leiningen](https://github.com/technomancy/leiningen)

## Usage

Hack on the `src/cljs/base_code/game.cljs`.

To test the game while recompiling the game code (when the source
changes) - the easiest way is to run:

    foreman start

(Make sure you have the foreman gem installed for that - `gem install foreman`)

This will start up a web server for you. [Open it in your browser](http://localhost:5100/), preferably Chrome or similarly-Canvas-capable.

## TODO

* Script to sync static assets into a CDN so "deployment" can be automated

## License

Copyright (C) 2012 William Roe

Distributed under the Eclipse Public License, the same as Clojure.
