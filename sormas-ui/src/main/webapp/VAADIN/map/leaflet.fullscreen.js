/*******************************************************************************
 * SORMAS® - Surveillance Outbreak Response Management & Analysis System
 * Copyright © 2016-2018 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
L.Control.Fullscreen=L.Control.extend({options:{position:"topleft",title:{"false":"View Fullscreen","true":"Exit Fullscreen"}},onAdd:function(map){var container=L.DomUtil.create("div","leaflet-control-fullscreen leaflet-bar leaflet-control");this.link=L.DomUtil.create("a","leaflet-control-fullscreen-button leaflet-bar-part",container);this.link.href="#";this._map=map;this._map.on("fullscreenchange",this._toggleTitle,this);this._toggleTitle();L.DomEvent.on(this.link,"click",this._click,this);return container},_click:function(e){L.DomEvent.stopPropagation(e);L.DomEvent.preventDefault(e);this._map.toggleFullscreen(this.options)},_toggleTitle:function(){this.link.title=this.options.title[this._map.isFullscreen()]}});L.Map.include({isFullscreen:function(){return this._isFullscreen||false},toggleFullscreen:function(options){var container=this.getContainer();if(this.isFullscreen()){if(options&&options.pseudoFullscreen){this._disablePseudoFullscreen(container)}else if(document.exitFullscreen){document.exitFullscreen()}else if(document.mozCancelFullScreen){document.mozCancelFullScreen()}else if(document.webkitCancelFullScreen){document.webkitCancelFullScreen()}else if(document.msExitFullscreen){document.msExitFullscreen()}else{this._disablePseudoFullscreen(container)}}else{if(options&&options.pseudoFullscreen){this._enablePseudoFullscreen(container)}else if(container.requestFullscreen){container.requestFullscreen()}else if(container.mozRequestFullScreen){container.mozRequestFullScreen()}else if(container.webkitRequestFullscreen){container.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT)}else if(container.msRequestFullscreen){container.msRequestFullscreen()}else{this._enablePseudoFullscreen(container)}}},_enablePseudoFullscreen:function(container){L.DomUtil.addClass(container,"leaflet-pseudo-fullscreen");this._setFullscreen(true);this.fire("fullscreenchange")},_disablePseudoFullscreen:function(container){L.DomUtil.removeClass(container,"leaflet-pseudo-fullscreen");this._setFullscreen(false);this.fire("fullscreenchange")},_setFullscreen:function(fullscreen){this._isFullscreen=fullscreen;var container=this.getContainer();if(fullscreen){L.DomUtil.addClass(container,"leaflet-fullscreen-on")}else{L.DomUtil.removeClass(container,"leaflet-fullscreen-on")}this.invalidateSize()},_onFullscreenChange:function(e){var fullscreenElement=document.fullscreenElement||document.mozFullScreenElement||document.webkitFullscreenElement||document.msFullscreenElement;if(fullscreenElement===this.getContainer()&&!this._isFullscreen){this._setFullscreen(true);this.fire("fullscreenchange")}else if(fullscreenElement!==this.getContainer()&&this._isFullscreen){this._setFullscreen(false);this.fire("fullscreenchange")}}});L.Map.mergeOptions({fullscreenControl:false});L.Map.addInitHook(function(){if(this.options.fullscreenControl){this.fullscreenControl=new L.Control.Fullscreen(this.options.fullscreenControl);this.addControl(this.fullscreenControl)}var fullscreenchange;if("onfullscreenchange"in document){fullscreenchange="fullscreenchange"}else if("onmozfullscreenchange"in document){fullscreenchange="mozfullscreenchange"}else if("onwebkitfullscreenchange"in document){fullscreenchange="webkitfullscreenchange"}else if("onmsfullscreenchange"in document){fullscreenchange="MSFullscreenChange"}if(fullscreenchange){var onFullscreenChange=L.bind(this._onFullscreenChange,this);this.whenReady(function(){L.DomEvent.on(document,fullscreenchange,onFullscreenChange)});this.on("unload",function(){L.DomEvent.off(document,fullscreenchange,onFullscreenChange)})}});L.control.fullscreen=function(options){return new L.Control.Fullscreen(options)};