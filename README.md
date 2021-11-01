# LRUCache

"In memory" type cache. For that, it uses HashMap, to store elements, as they are inserted into the LRUCache itself.
It has a pre-defined size, and every time it reaches full capacity, evicts the least recently used element.
All methods, beside removeElement(Object) and cacheHitAdd(Object, Object), are O(1) time complexity operations (Future updates may improve those as well).
