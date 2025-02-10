import java.util.*;

class Node {
    int key, value;
    Node prev, next;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

class LRUCache {
    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();

       
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    
    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1; // Key not found
        }
        Node node = cache.get(key);
        remove(node); // Remove from current position
        addToHead(node); // Move to head 
        return node.value;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            
            Node node = cache.get(key);
            node.value = value;
            remove(node);
            addToHead(node);
        } else {
            if (cache.size() >= capacity) {
                
                Node lru = tail.prev;
                remove(lru);
                cache.remove(lru.key);
            }
            // Insert new node
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToHead(newNode);
        }
    }

    
    public void display() {
        Node current = head.next;
        while (current != tail) {
            System.out.print("(" + current.key + ": " + current.value + ") <--> ");
            current = current.next;
        }
        System.out.println("NULL");
    }

    public static void main(String[] args) {
        LRUCache lru = new LRUCache(3);
        lru.put(1, 10);
        lru.put(2, 20);
        lru.put(3, 30);
        lru.display(); 

        lru.get(2); 
        lru.display();

        lru.put(4, 40); 
        lru.display(); 
    }
}
