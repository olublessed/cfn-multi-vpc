aws {
    profileName = artifacts3.profileName
    region = 'us-east-2'
}

cloudFormation {
    templateFile = project.file('build/cloudformation/multi-vpc.yaml')
    stackName = 'test-stack'
    capabilityIam true
    conventionMapping.stackParams = {
        return stackParams = [
            ProjectName: 'ExampleApp',
            NotificationEmail: 'andy@cfn-stacks.com',
            AvailabilityZone1: 'us-east-2a',
            AvailabilityZone2: 'us-east-2b',
            StackTemplates: 'https://s3.amazonaws.com/cfn-stacks.com/templates',
            InfraTemplates: 'snapshot/com/amazon/aws/cfn-compliance-common/0.0.3-SNAPSHOT',
            bCreateProductionVPC: true,
            bCreateStagingVPC: false,
            bCreateTestVPC: false,
            bCreateDevVPC: true,
            allowedCIDR: '0.0.0.0/0',
            pEC2KeyPairBastion: 'raptor',
            pEC2KeyPair: 'raptor'
        ]
    }
}