package com.study.workFlow;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.jeasy.flows.engine.WorkFlowEngine;
import org.jeasy.flows.work.*;
import org.jeasy.flows.workflow.ParallelFlow;
import org.jeasy.flows.workflow.SequentialFlow;
import org.jeasy.flows.workflow.WorkFlow;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;

import static org.jeasy.flows.engine.WorkFlowEngineBuilder.aNewWorkFlowEngine;
import static org.jeasy.flows.work.WorkReportPredicate.COMPLETED;
import static org.jeasy.flows.workflow.ConditionalFlow.Builder.aNewConditionalFlow;
import static org.jeasy.flows.workflow.ParallelFlow.Builder.aNewParallelFlow;
import static org.jeasy.flows.workflow.RepeatFlow.Builder.aNewRepeatFlow;
import static org.jeasy.flows.workflow.SequentialFlow.Builder.aNewSequentialFlow;

public class testWorkFlow {

    public static void main(String[] args){
        //defineWorkFlowInlineAndExecuteIt();
        //useWorkContextToPassInitialParametersAndShareDataBetweenWorkUnits();
        constructDAGFlow();
    }

    private static void constructDAGFlow() {
        Map<Integer,Node> idToNode = new HashMap<>();
        //图1
       /* Node node1 = new Node(1,new ArrayList<>(),Arrays.asList(2,4));
        idToNode.put(1,node1);
        Node node2 = new Node(2,Arrays.asList(1),Arrays.asList(3));
        idToNode.put(2,node2);
        Node node4 = new Node(4,Arrays.asList(1),Arrays.asList(3));
        idToNode.put(4,node4);
        Node node3 = new Node(3,Arrays.asList(2,4),Arrays.asList(5));
        idToNode.put(3,node3);
        Node node5 = new Node(5,Arrays.asList(3),new ArrayList<>());
        idToNode.put(5,node5);*/
        //图2
        /*Node node1 = new Node(1,new ArrayList<>(),Arrays.asList(2,4));
        idToNode.put(1,node1);
        Node node2 = new Node(2,Arrays.asList(1),Arrays.asList(3));
        idToNode.put(2,node2);
        Node node4 = new Node(4,Arrays.asList(1),Arrays.asList(5));
        idToNode.put(4,node4);
        Node node3 = new Node(3,Arrays.asList(2,5),Arrays.asList(7));
        idToNode.put(3,node3);
        Node node5 = new Node(5,Arrays.asList(4),Arrays.asList(3,6));
        idToNode.put(5,node5);
        Node node6 = new Node(6,Arrays.asList(5),new ArrayList<>());
        idToNode.put(6,node6);
        Node node7 = new Node(7,Arrays.asList(3),new ArrayList<>());
        idToNode.put(7,node7);*/
        //图3
        /*Node node1 = new Node(1,new ArrayList<>(),Arrays.asList(2,4));
        idToNode.put(1,node1);
        Node node2 = new Node(2,Arrays.asList(1),Arrays.asList(3));
        idToNode.put(2,node2);
        Node node4 = new Node(4,Arrays.asList(1),Arrays.asList(5));
        idToNode.put(4,node4);
        Node node3 = new Node(3,Arrays.asList(2),Arrays.asList(6,8));
        idToNode.put(3,node3);
        Node node5 = new Node(5,Arrays.asList(4),Arrays.asList(6,7));
        idToNode.put(5,node5);
        Node node6 = new Node(6,Arrays.asList(3,5),Arrays.asList(9));
        idToNode.put(6,node6);
        Node node7 = new Node(7,Arrays.asList(5),new ArrayList<>());
        idToNode.put(7,node7);
        Node node8 = new Node(8,Arrays.asList(3),new ArrayList<>());
        idToNode.put(8,node8);
        Node node9 = new Node(9,Arrays.asList(6),new ArrayList<>());
        idToNode.put(9,node9);*/
        //图4
        /*Node node1 = new Node(1,new ArrayList<>(),Arrays.asList(2,3,4));
        idToNode.put(1,node1);
        Node node2 = new Node(2,Arrays.asList(1),Arrays.asList(5));
        idToNode.put(2,node2);
        Node node4 = new Node(3,Arrays.asList(1),Arrays.asList(5,6));
        idToNode.put(3,node4);
        Node node3 = new Node(4,Arrays.asList(1),Arrays.asList(6));
        idToNode.put(4,node3);
        Node node5 = new Node(5,Arrays.asList(2,3),Arrays.asList(7));
        idToNode.put(5,node5);
        Node node6 = new Node(6,Arrays.asList(3,4),Arrays.asList(8));
        idToNode.put(6,node6);
        Node node7 = new Node(7,Arrays.asList(5),new ArrayList<>());
        idToNode.put(7,node7);
        Node node8 = new Node(8,Arrays.asList(6),new ArrayList<>());
        idToNode.put(8,node8);*/
        //图5
        /*Node node1 = new Node(1,new ArrayList<>(),Arrays.asList(2,3));
        idToNode.put(1,node1);
        Node node2 = new Node(2,Arrays.asList(1),Arrays.asList(4));
        idToNode.put(2,node2);
        Node node4 = new Node(3,Arrays.asList(1),Arrays.asList(4,5));
        idToNode.put(3,node4);
        Node node3 = new Node(4,Arrays.asList(2,3),Arrays.asList(6));
        idToNode.put(4,node3);
        Node node5 = new Node(5,Arrays.asList(3),Arrays.asList(6,7));
        idToNode.put(5,node5);
        Node node6 = new Node(6,Arrays.asList(4,5),new ArrayList<>());
        idToNode.put(6,node6);
        Node node7 = new Node(7,Arrays.asList(5),new ArrayList<>());
        idToNode.put(7,node7);*/
        //图6
        /*Node node1 = new Node(1,new ArrayList<>(),Arrays.asList(2));
        idToNode.put(1,node1);
        Node node2 = new Node(2,Arrays.asList(1),Arrays.asList(3,4));
        idToNode.put(2,node2);
        Node node4 = new Node(4,Arrays.asList(2),Arrays.asList(3,5));
        idToNode.put(4,node4);
        Node node3 = new Node(3,Arrays.asList(2,4),Arrays.asList(6));
        idToNode.put(3,node3);
        Node node5 = new Node(5,Arrays.asList(4),new ArrayList<>());
        idToNode.put(5,node5);
        Node node6 = new Node(6,Arrays.asList(3),new ArrayList<>());
        idToNode.put(6,node6);*/
        //图7
        Node node1 = new Node(1,new ArrayList<>(),Arrays.asList(2));
        idToNode.put(1,node1);
        Node node2 = new Node(2,Arrays.asList(1),Arrays.asList(3,4));
        idToNode.put(2,node2);
        Node node4 = new Node(4,Arrays.asList(2),Arrays.asList(3,5));
        idToNode.put(4,node4);
        Node node3 = new Node(3,Arrays.asList(2,4),Arrays.asList(6));
        idToNode.put(3,node3);
        Node node5 = new Node(5,Arrays.asList(4),Arrays.asList(6));
        idToNode.put(5,node5);
        Node node6 = new Node(6,Arrays.asList(3,5),new ArrayList<>());
        idToNode.put(6,node6);


        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Node> BFSNodes = BFSNodes(1,idToNode);
        /*WorkFlow workFlow = constructorBFSFlow(1,BFSNodes,executorService);*/
        WorkFlow workFlow = constructorBFSFlowOne(1,BFSNodes,idToNode,executorService);

        //后序遍历，flow可清晰看着路径节点前后关系，但逻辑复杂可能有的情况未包含进去，慎用。
        /*List<Node> postOrderNodes = postOrderNodes(1,idToNode);
        WorkFlow workFlow = constructorPostOrderFlow(1,postOrderNodes,idToNode,executorService);*/

        WorkContext workContext = new WorkContext();
        WorkFlowEngine workFlowEngine = aNewWorkFlowEngine().build();
        WorkReport workReport = workFlowEngine.run(workFlow, workContext);
        executorService.shutdown();
        
        System.out.println("END");


    }

    private static SequentialFlow constructorBFSFlowOne(int inputNodeId, List<Node> bfsResultNodes, Map<Integer,Node> idToNode, ExecutorService executorService) {
        SequentialFlow.Builder.ThenStep midFlow = null;
        List<Integer> alreadyNodeIds = new ArrayList<>(); //存储已加入flow中的节点
        Map<Integer,Work> waitNodeMap = new HashMap<>(); //存储待加入flow中的节点
        List<Integer> midNodeIds = new ArrayList<>(); //存储已加入flow中的节点
        for(Node nodeWorkDTO: bfsResultNodes){
            if(nodeWorkDTO.id == inputNodeId){ //头节点构建flow,并加入到doneNodeId中
                midFlow = aNewSequentialFlow().execute(nodeWorkDTO);
                alreadyNodeIds.add(inputNodeId);
            }else{
                if(midNodeIds.contains(Integer.valueOf(nodeWorkDTO.id))){continue;}
                //如果父节点已在构建流alreadyNodeIds中，加入待构建节点waitNodeMap，否则构建取出所有待排节点构建并行流
                if(alreadyNodeIds.containsAll(nodeWorkDTO.preNodeId)){
                    checkNext(idToNode, waitNodeMap, midNodeIds, nodeWorkDTO);
                }else{
                    if(waitNodeMap.isEmpty()) {continue;}
                    Work[] waitNodes = waitNodeMap.values().toArray(new Work[waitNodeMap.values().size()]);
                    ParallelFlow parallelFlow = aNewParallelFlow()
                            .named("parallel")
                            .execute(waitNodes)
                            .with(executorService)
                            .build();
                    midFlow.then(parallelFlow);
                    //将节点加入已完成，清空待排节点，将新节点加入waitNodeMap
                    alreadyNodeIds.addAll(waitNodeMap.keySet());
                    alreadyNodeIds.addAll(midNodeIds);
                    waitNodeMap.clear();

                    checkNext(idToNode, waitNodeMap, midNodeIds, nodeWorkDTO);

                }
            }
        }
        //待节点未空，取出构建flow
        if(!waitNodeMap.isEmpty()){
            Work[] waitNodes = waitNodeMap.values().toArray(new Work[waitNodeMap.values().size()]);
            ParallelFlow parallelFlow = aNewParallelFlow()
                    .named("parallel")
                    .execute(waitNodes)
                    .with(executorService)
                    .build();
            midFlow.then(parallelFlow);
        }
        SequentialFlow resultFlow = midFlow.build();
        return resultFlow;
    }

    private static void checkNext(Map<Integer, Node> idToNode, Map<Integer, Work> waitNodeMap, List<Integer> midNodeIds, Node nodeWorkDTO) {
        int loop = 0;
        SequentialFlow.Builder.ThenStep tempMidFlow = aNewSequentialFlow().execute(nodeWorkDTO);
        Node tempNode = nodeWorkDTO;
        while(!CollectionUtils.isEmpty(tempNode.nextNodeId)
                && tempNode.nextNodeId.size() == 1
                && idToNode.get(tempNode.nextNodeId.get(0)).preNodeId.size()==1){
            tempMidFlow = tempMidFlow.then(idToNode.get(tempNode.nextNodeId.get(0)));
            midNodeIds.add(tempNode.nextNodeId.get(0));
            loop++;
            tempNode = idToNode.get(tempNode.nextNodeId.get(0));
        }
        SequentialFlow tempFlow = tempMidFlow.build();

        if(loop==0){
            waitNodeMap.put(nodeWorkDTO.id, nodeWorkDTO);
        }else{
            waitNodeMap.put(nodeWorkDTO.id,tempFlow);
        }
    }

    private static SequentialFlow constructorBFSFlow(int inputNodeId, List<Node> bfsResultNodes, ExecutorService executorService) {
        SequentialFlow.Builder.ThenStep midFlow = null;
        List<Integer> alreadyNodeIds = new ArrayList<>(); //存储已加入flow中的节点
        Map<Integer,Node> waitNodeMap = new HashMap<>(); //存储待加入flow中的节点
        for(Node nodeWorkDTO: bfsResultNodes){
            if(nodeWorkDTO.id == inputNodeId){ //头节点构建flow,并加入到doneNodeId中
                midFlow = aNewSequentialFlow().execute(nodeWorkDTO);
                alreadyNodeIds.add(inputNodeId);
            }else{
                //如果父节点已在构建流doneNodeId中，加入待构建节点waitNodeMap，否则构建取出所有待排节点构建并行流
                if(alreadyNodeIds.containsAll(nodeWorkDTO.preNodeId)){
                    waitNodeMap.put(nodeWorkDTO.id,nodeWorkDTO);
                }else{
                    if(waitNodeMap.isEmpty()) {continue;}
                    Node[] waitNodes = waitNodeMap.values().toArray(new Node[waitNodeMap.values().size()]);
                    ParallelFlow parallelFlow = aNewParallelFlow()
                            .named("parallel")
                            .execute(waitNodes)
                            .with(executorService)
                            .build();
                    midFlow.then(parallelFlow);
                    //将节点加入已完成，清空待排节点，将新节点加入waitNodeMap
                    alreadyNodeIds.addAll(waitNodeMap.keySet());
                    waitNodeMap.clear();
                    waitNodeMap.put(nodeWorkDTO.id,nodeWorkDTO);
                }
            }
        }
        //待节点未空，取出构建flow
        if(!waitNodeMap.isEmpty()){
            Node[] waitNodes = waitNodeMap.values().toArray(new Node[waitNodeMap.values().size()]);
            ParallelFlow parallelFlow = aNewParallelFlow()
                    .named("parallel")
                    .execute(waitNodes)
                    .with(executorService)
                    .build();
            midFlow.then(parallelFlow);
        }
        SequentialFlow resultFlow = midFlow.build();
        return resultFlow;
    }


    private static WorkFlow constructorPostOrderFlow(int inputNodeId, List<Node> postOrderResultNodes,Map<Integer,Node> idToNode,ExecutorService executorService) {

        SequentialFlow sequentialFlow = null;
        SequentialFlow.Builder.ThenStep midFlow;
        Map<Integer,Work> waitWorkMap = new HashMap<>();//待处理节点。存储已处理节点，或前一个节点没处理的节点
        Map<Integer,List<Integer>> idToNextId = new HashMap<>(); //待处理节点中。前节点后节点对应关系
        for(Node node: postOrderResultNodes){
            //没后节点
            if(CollectionUtils.isEmpty(node.nextNodeId)){
                waitWorkMap.put(node.id,node);
                continue;
            }
            //有一个后节点
            if(node.nextNodeId.size()==1){
                //后节点有多个前节点
                if(idToNode.get(node.nextNodeId.get(0)).preNodeId.size()>1){
                    waitWorkMap.put(node.id,node);
                    if(CollectionUtils.isEmpty(idToNextId.get(node.id))){
                        List<Integer> nextId= new ArrayList<>();
                        nextId.add(node.nextNodeId.get(0));
                        idToNextId.put(node.id,nextId);
                    }else{
                        idToNextId.get(node.id).add(node.nextNodeId.get(0));
                    }
                }
                //后节点有一个前节点
                if(idToNode.get(node.nextNodeId.get(0)).preNodeId.size()==1){
                    midFlow = aNewSequentialFlow().execute(node);
                    sequentialFlow = midFlow.then(waitWorkMap.get(node.nextNodeId.get(0))).build();
                    waitWorkMap.remove(node.nextNodeId.get(0));
                    waitWorkMap.put(node.id,sequentialFlow);
                    //后节点有合并节点
                    if(!CollectionUtils.isEmpty(idToNextId.get(node.nextNodeId.get(0)))){
                        idToNextId.put(node.id,idToNextId.get(node.nextNodeId.get(0)));
                        idToNextId.remove(node.nextNodeId.get(0));
                    }
                }
            }
            //有两个后节点
            if(node.nextNodeId.size()>1){ //有两个以上后节点，构建并行流

                List<Integer> nextIdMulPres = node.nextNodeId.stream().filter(nextId -> idToNode.get(nextId).preNodeId.size() > 1).collect(Collectors.toList());

                //剔除 后节点前节点是自己后节点 的边 。如图7，2->4,4->3,2->3,可剔除边4-3。
                if(!CollectionUtils.isEmpty(nextIdMulPres)){
                    List<Integer> nextIds = new ArrayList<>();
                    for(int nextId : nextIdMulPres){
                        List<Integer> commonNodeIds = (List<Integer>)CollectionUtils.intersection(idToNode.get(nextId).preNodeId,node.nextNodeId);
                        if(!CollectionUtils.isEmpty(commonNodeIds)){
                            for(int commonNodeId:commonNodeIds){
                                if(!CollectionUtils.isEmpty(idToNextId.get(commonNodeId))){
                                    if(idToNextId.get(commonNodeId).size() == 1){
                                        idToNextId.remove(commonNodeId);
                                    }else{
                                        idToNextId.get(commonNodeId).remove(Integer.valueOf(nextId));
                                    }
                                    //剔除边后，如果剩一个后节点，且后节点有此一个前节点，整个两个flow
                                    if(idToNextId.get(commonNodeId).size() == 1 && idToNode.get(idToNextId.get(commonNodeId).get(0)).preNodeId.size() ==1){
                                        sequentialFlow = aNewSequentialFlow().execute(waitWorkMap.get(commonNodeId)).then(waitWorkMap.get(idToNextId.get(commonNodeId).get(0))).build();
                                        waitWorkMap.put(commonNodeId,sequentialFlow);
                                        waitWorkMap.remove(idToNextId.get(commonNodeId).get(0));
                                        if(!CollectionUtils.isEmpty(idToNextId.get(idToNextId.get(commonNodeId).get(0)))){
                                            int nextNodeId = idToNextId.get(commonNodeId).get(0);
                                            idToNextId.put(commonNodeId,idToNextId.get(nextNodeId));
                                            idToNextId.remove(nextNodeId);
                                        }else{
                                            idToNextId.remove(commonNodeId);
                                        }
                                    }
                                }
                            }
                            nextIds.add(nextId);
                        }

                    }
                    if(!CollectionUtils.isEmpty(nextIds)){
                        nextIdMulPres.removeAll(nextIds);
                    }
                }

                //后节点有多个前节点，加入
                if(!CollectionUtils.isEmpty(nextIdMulPres)){
                    waitWorkMap.put(node.id,node);
                    for(int nextIdMulPre : node.nextNodeId){
                        if(CollectionUtils.isEmpty(idToNextId.get(node.id))){
                            List<Integer> nextId= new ArrayList<>();
                            nextId.add(nextIdMulPre);
                            idToNextId.put(node.id,nextId);
                        }else{
                            idToNextId.get(node.id).add(nextIdMulPre);
                        }
                    }
                }else{ //后节点的前节点仅当前一个
                    //获取子节点构建Flow
                    Set<Work> childNodes = node.nextNodeId.stream().map(childNodeId -> waitWorkMap.get(childNodeId)).collect(Collectors.toSet());
                    ParallelFlow parallelFlow = aNewParallelFlow()
                            .execute(childNodes.toArray(new Work[childNodes.size()]))
                            .with(executorService)
                            .build();
                    //构建该节点flow
                    midFlow = aNewSequentialFlow().execute(node).then(parallelFlow);
                    //从waitNodeMap中移出子节点
                    node.nextNodeId.forEach(childNodeId -> waitWorkMap.remove(childNodeId));

                    //如果子节点有合并下游
                    List<Integer> nextNodeIds = node.nextNodeId;
                    while(!CollectionUtils.isEmpty(nextNodeIds)){
                        //获取合并节点
                        Set<Integer> childChildIdSet = new HashSet<>();
                        for(int childId : nextNodeIds){
                            if(!CollectionUtils.isEmpty(idToNextId.get(childId))){
                                for(int childChildId : idToNextId.get(childId)){
                                    childChildIdSet.add(childChildId);
                                }
                            }
                        }
                        List<Integer> childChildNode = new ArrayList<>(childChildIdSet);
                        //对合并节点构建flow
                        if(!CollectionUtils.isEmpty(childChildNode)){
                            Set<Work> childChildNodes = childChildNode.stream().map(childId -> waitWorkMap.get(childId)).collect(Collectors.toSet());
                            ParallelFlow parallelChildFlow = aNewParallelFlow()
                                    .execute(childChildNodes.toArray(new Work[childChildNodes.size()]))
                                    .with(executorService)
                                    .build();
                            midFlow.then(parallelChildFlow);
                            childChildNode.forEach(childChildId -> waitWorkMap.remove(childChildId));
                        }else{
                            break;
                        }
                        //剔除idToNextId关系
                        nextNodeIds.forEach(childId -> idToNextId.remove(childId));
                        //将下节点赋给当前节点
                        nextNodeIds = childChildNode;
                    }

                    sequentialFlow = midFlow.build();
                    waitWorkMap.put(node.id,sequentialFlow);


                }

            }

        }

        return (WorkFlow)waitWorkMap.get(inputNodeId);
    }

    private static List<Node> BFSNodes(int inputNodeId, Map<Integer, Node> idToNode) {

        List<Node> NodeWorkList = new ArrayList<>(); //存储遍历结果
        Queue<Node> tempNodeQueue = new LinkedList<>(); //用队列临时存储
        List<Integer> alreadyNodeId = new ArrayList<>(); //存储已经存在结果中节点id

        //遍历
        tempNodeQueue.offer(idToNode.get(inputNodeId));//入径节点进队
        while (!tempNodeQueue.isEmpty()) {
            Node node = tempNodeQueue.poll();//出队
            NodeWorkList.add(node);
            alreadyNodeId.add(node.id);
            if (!CollectionUtils.isEmpty(node.nextNodeId)) {
                for(int nextNodeId:node.nextNodeId){
                    if(alreadyNodeId.containsAll(idToNode.get(nextNodeId).preNodeId)){ //判断某个节点的父节点是否已全部出队。
                        tempNodeQueue.offer(idToNode.get(nextNodeId)); //节点进队
                    }
                }

            }
        }

        return NodeWorkList;

    }

    private static List<Node> postOrderNodes(int inputNodeId, Map<Integer, Node> idToNode) {

        Stack<Node> tempNodeStack = new Stack<>(); //用栈临时存储
        Stack<Node> resultNodeStack = new Stack<>();//遍历结果倒序存储，入径节点在栈底。
        List<Integer> alreadyIds = new ArrayList<>();
        //遍历
        tempNodeStack.push(idToNode.get(inputNodeId));//入径节点进栈
        while (!tempNodeStack.empty()) {
            Node node = tempNodeStack.pop();//出栈
            resultNodeStack.add(node);
            alreadyIds.add(node.id);
            if (!CollectionUtils.isEmpty(node.nextNodeId)) {
                for(int nextId : node.nextNodeId){
                    if(alreadyIds.containsAll(idToNode.get(nextId).preNodeId)){
                        tempNodeStack.push(idToNode.get(nextId));
                    }
                }
            }
        }
        //处理遍历结果从栈结构转为列表
        List<Node> NodeWorkList = new ArrayList<>();
        while (!resultNodeStack.empty()) {
            NodeWorkList.add(resultNodeStack.pop());
        }

        return NodeWorkList;

    }

    private static void defineWorkFlowInlineAndExecuteIt() {

        PrintMessageWork work1 = new PrintMessageWork("foo");
        PrintMessageWork work2 = new PrintMessageWork("hello");
        PrintMessageWork work3 = new PrintMessageWork("world");
        PrintMessageWork work4 = new PrintMessageWork("done");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        WorkFlow workflow = aNewSequentialFlow()
                .execute(aNewRepeatFlow()
                        .named("print foo 3 times")
                        .repeat(work1)
                        .times(3)
                        .build())
                .then(aNewConditionalFlow()
                        .execute(aNewParallelFlow()
                                .named("print 'hello' and 'world' in parallel")
                                .execute(work2, work3)
                                .with(executorService)
                                .build())
                        .when(COMPLETED)
                        .then(work4)
                        .build())
                .build();

        WorkFlowEngine workFlowEngine = aNewWorkFlowEngine().build();
        WorkContext workContext = new WorkContext();
        WorkReport workReport = workFlowEngine.run(workflow, workContext);
        executorService.shutdown();
        System.out.println("workflow report = " + workReport);
    }

    static class PrintMessageWork implements Work {

        private final String message;

        public PrintMessageWork(String message) {
            this.message = message;
        }

        public String getName() {
            return "print message work";
        }

        public WorkReport execute(WorkContext workContext) {
            System.out.println(message);
            return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
        }

    }

    private static void useWorkContextToPassInitialParametersAndShareDataBetweenWorkUnits() {
        WordCountWork work1 = new WordCountWork(1);
        WordCountWork work2 = new WordCountWork(2);
        AggregateWordCountsWork work3 = new AggregateWordCountsWork();
        System.out.println(work3.getMessage());
        PrintWordCount work4 = new PrintWordCount();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        WorkFlow workflow = aNewSequentialFlow()
                .execute(aNewParallelFlow()
                        .execute(work1, work2)
                        .with(executorService)
                        .build())
                .then(work3)
                .then(work4)
                .build();

        WorkFlowEngine workFlowEngine = aNewWorkFlowEngine().build();
        WorkContext workContext = new WorkContext();
        workContext.put("partition1", "hello foo");
        workContext.put("partition2", "hello bar");
        WorkReport workReport = workFlowEngine.run(workflow, workContext);
        executorService.shutdown();
        System.out.println(workReport);
        System.out.println(work3.getMessage());
    }


    static class WordCountWork implements Work {

        private final int partition;

        public WordCountWork(int partition) {
            this.partition = partition;
        }

        @Override
        public String getName() {
            return "count words in a given string";
        }

        @Override
        public WorkReport execute(WorkContext workContext) {
            String input = (String) workContext.get("partition" + partition);
            workContext.put("wordCountInPartition" + partition, input.split(" ").length);
            List<String> list = Arrays.asList("开始","结束");
            workContext.put("list",list);
            return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
        }
    }

    static class AggregateWordCountsWork implements Work {

        private String message = "message start";

        @Override
        public String getName() {
            return "aggregate word counts from partitions";
        }

        public String getMessage() {
            return message;
        }

        @Override
        public WorkReport execute(WorkContext workContext) {
            Set<Map.Entry<String, Object>> entrySet = workContext.getEntrySet();
            int sum = 0;
            for (Map.Entry<String, Object> entry : entrySet) {
                if (entry.getKey().contains("InPartition")) {
                    sum += (int) entry.getValue();
                }
            }
            workContext.put("totalCount", sum);
            message = "message end";
            return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
        }
    }

    static class PrintWordCount implements Work {

        @Override
        public String getName() {
            return "print total word count";
        }

        @Override
        public WorkReport execute(WorkContext workContext) {
            int totalCount = (int) workContext.get("totalCount");
            System.out.println(totalCount);
            List<String> list = (List<String>) workContext.get("list");
            return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
        }
    }

    static class Node implements Work{
        private int id;
        private List<Integer> preNodeId = new ArrayList<>();
        private List<Integer> nextNodeId = new ArrayList<>();

        public Node(int id, List<Integer> preNodeId, List<Integer> nextNodeId) {
            this.id = id;
            this.preNodeId = preNodeId;
            this.nextNodeId = nextNodeId;
        }

        @Override
        public String getName() {
            return "节点ID:"+id+"；前节点:"+preNodeId.toString()+"; 后节点:"+nextNodeId.toString();
        }

        @Override
        public WorkReport execute(WorkContext workContext) {
            System.out.println(getName());
            return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
        }
    }

    static class NodeOne implements Work{
        private int id;
        private List<Integer> preNodeId = new ArrayList<>();
        private List<Integer> nextNodeId = new ArrayList<>();

        private Map<Integer, Boolean> preStatus = new ConcurrentHashMap<>();

        public NodeOne(int id, List<Integer> preNodeId, List<Integer> nextNodeId) {
            this.id = id;
            this.preNodeId = preNodeId;
            this.nextNodeId = nextNodeId;
            for(int preId : preNodeId){
                preStatus.put(preId,false);
            }
        }

        public void setStatus(int perId){
            preStatus.put(perId,true);
            if(checkStatus()){
                LockSupport.park();
            }
        }

        public boolean checkStatus(){
            if(CollectionUtils.isEmpty(preStatus.values().stream().filter(e->!e).collect(Collectors.toSet()))){
                return true;
            }
            return false;
        }


        @Override
        public String getName() {
            return "节点ID:"+id+"；前节点:"+preNodeId.toString()+"; 后节点:"+nextNodeId.toString();
        }

        @Override
        public WorkReport execute(WorkContext workContext) {
            LockSupport.park();
            System.out.println(getName());
            return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
        }
    }


}
