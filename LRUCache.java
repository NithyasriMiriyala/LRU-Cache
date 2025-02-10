import java.util.*; // Importing necessary classes for HashMap

// LRUCache class using Circular Doubly Linked List (CDLL) and HashMap
class LRUCache {
    CDLL ll; // Circular Doubly Linked List to maintain access order
    int capacity; // Maximum capacity
    int size; // Current size
    Map<Integer, CDLLNode> mp; // HashMap to store key -> Node mapping for O(1) lookup

    // Initializes cache with given capacity
    public LRUCache(int cap) {
        ll = new CDLL(); // Initialize the Circular Doubly Linked List
        this.capacity = cap; // Set the cache capacity
        this.size = 0; //  cache size is 0
        mp = new HashMap<>(); // Initialize HashMap for fast access
    }

    // Retrieves the value of a key if present in cache
    int get(int k) {
        if (mp.containsKey(k)) { // If key exists in cache
            CDLLNode node = mp.get(k); // Fetch the node from HashMap
            ll.moveToFront(node); // Move the accessed node to the front (MRU)
            return node.val; // Return the value
        } else {
            return -1; // Key not found, return -1
        }
    }

    // Inserts or updates a key-value pair in the cache
    void put(int k, int v) {
        if (mp.containsKey(k)) { // If key already exists in cache, update it
            CDLLNode node = mp.get(k); // Fetch the node
            node.val = v; // Update its value
            ll.moveToFront(node); // Move updated node to the front (MRU)
        } else { // If key does not exist, insert it
            if (size < capacity) { // If cache is not full
                CDLLNode nn = ll.insAtBegin(k, v); // Insert new node at the front
                mp.put(k, nn); // Store it in HashMap
                size++; // Increment cache size
            } else { // If cache is full, evict LRU node
                int delKey = ll.delLast(); // Remove least recently used node
                mp.remove(delKey); // Remove evicted key from HashMap
                CDLLNode nn = ll.insAtBegin(k, v); // Insert new node at the front
                mp.put(k, nn); // Store new key in HashMap
            }
        }
    }
}

// Class representing a node in the Circular Doubly Linked List
class CDLLNode {
    int key, val; 
    CDLLNode prev, next; // Pointers for doubly linked list

    
    public CDLLNode(int k, int v) {
        this.key = k;
        this.val = v;
        this.prev = this.next = null; // Initially, node is isolated
    }
}

// Circular Doubly Linked List (CDLL) implementation
class CDLL {
    CDLLNode head; // Head of the linked list (Most Recently Used - MRU)

    // Constructor: Initializes an empty list
    public CDLL() {
        head = null;
    }

    // Inserts a new node at the beginning (MRU position)
    CDLLNode insAtBegin(int k, int v) {
        CDLLNode nn = new CDLLNode(k, v); 
        nn.next = nn;
        nn.prev = nn; // Initially, it points to itself (circular nature)

        if (head == null) { // If list is empty, set this node as head
            head = nn;
            return nn;
        }

        CDLLNode last = head.prev; // Get the last node
        nn.next = head; // New node points to current head
        head.prev = nn; // Head's previous pointer updated to new node
        last.next = nn; // Last node's next pointer updated to new node
        nn.prev = last; // New node's previous pointer updated to last node
        head = nn; // Update head to new node (most recently used)
        return nn;
    }

   
    void printLL() {
        if (head == null) return; // If list is empty, do nothing
        System.out.print(head.key + " "); // Print head node
        CDLLNode curr = head.next; // Start from next node
        while (curr != head) { // Traverse until we return to head
            System.out.print(curr.key + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    
    int delLast() {
        if (head == null) return -1; // If list is empty, return -1
        CDLLNode last = head.prev; // Get the last node
        int ret = last.key; // Store its key to return later

        if (last == head) { // If there's only one node
            head = null; 
            return ret;
        }

        last.prev.next = head; 
        head.prev = last.prev; // Update head's previous pointer
        return ret; // Return removed key
    }

    
    void moveToFront(CDLLNode nodeToMove) {
        if (nodeToMove == head) return; // If it's already the head

        // Remove node from its current position
        nodeToMove.prev.next = nodeToMove.next;
        nodeToMove.next.prev = nodeToMove.prev;

        CDLLNode last = head.prev; // Get the last node
        nodeToMove.next = head; // Update next pointer to head
        head.prev = nodeToMove; // Update head's previous pointer
        last.next = nodeToMove; // Last node's next pointer updated
        nodeToMove.prev = last; // Node's previous pointer updated to last node
        head = nodeToMove; // Update head to this node
    }
}
