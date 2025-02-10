class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.prev = None
        self.next = None

class LRUCache:
    def __init__(self, capacity: int):
        self.capacity = capacity
        self.cache = {}  # HashMap: { key -> Node }
        
       
        self.head = Node(0, 0)  
        self.tail = Node(0, 0) 
        self.head.next = self.tail
        self.tail.prev = self.head

    def _remove(self, node):
       
        prev_node = node.prev
        next_node = node.next
        prev_node.next = next_node
        next_node.prev = prev_node

    def _add_to_head(self, node):
       
        node.next = self.head.next
        node.prev = self.head
        self.head.next.prev = node
        self.head.next = node

    def get(self, key: int) -> int:
       
        if key not in self.cache:
            return -1  
        
        node = self.cache[key]
        self._remove(node) 
        self._add_to_head(node)  
        return node.value

    def put(self, key: int, value: int):
        """Inserts or updates a key-value pair in the cache."""
        if key in self.cache:
           
            node = self.cache[key]
            node.value = value
            self._remove(node)
            self._add_to_head(node)
        else:
            if len(self.cache) >= self.capacity:
                
                lru = self.tail.prev
                self._remove(lru)
                del self.cache[lru.key]

           
            new_node = Node(key, value)
            self.cache[key] = new_node
            self._add_to_head(new_node)

    def display(self):
       
        node = self.head.next
        result = []
        while node != self.tail:
            result.append(f"({node.key}: {node.value})")
            node = node.next
        print(" <--> ".join(result))

# Example usage:
lru = LRUCache(3)
lru.put(1, 10)
lru.put(2, 20)
lru.put(3, 30)
lru.display()  

lru.get(2)  
lru.display()  

lru.put(4, 40)  
lru.display()  
