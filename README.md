# Graph networks

## Problem description

We have a set of elements. In this example we will use eight.
1 2 3 4 5 6 7 8
We can make a set of connections. For example, we can connect 1 to 6.  
1 2 3 4 5   
|  
6 7 8

We can make any number of connections and any two elements can be connected. 
Let’s make the following connections: 1-2, 6-2, 2-4, 5-8

Now we need to be able to determine if two elements are connected,   
either directly or through a series of connections.   
1 and 6 are connected, as are 6 and 4.   
But 7 and 4 are not connected, neither are 5 and 6.   
We do not care about the path, 1 and 2 are connected   
both directly and also through 6, but for this problem the fact that there are two paths is irrelevant.


## Implementation details
We're using a tree of nodes to map the connections between the graph nodes.  
At the same time, we're keeping a cache of the input numbers to their respective nodes for performance reasons. That cache has been implemented using a HashMap.  

We're using path compression to keep the tree as flat as possible.  
The reason for that is that the find operation requires finding the root nodes of both input numbers. And the flattest the tree the quickest it is to find their respective tree root nodes.